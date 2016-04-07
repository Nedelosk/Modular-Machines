package de.nedelosk.forestmods.api.transport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.nedelosk.forestcore.utils.BlockPos;
import de.nedelosk.forestcore.utils.Log;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class TransportWorldRegistry {

	private World worldObj;
	private Set<ITransportSystem> systems;
	private Set<ITransportSystem> dirtySystems;
	private Set<ITransportSystem> deadSystem;
	private Set<ITransportPart> orphanedParts;
	private Set<ITransportPart> detachedParts;
	private HashMap<Long, Set<ITransportPart>> partsAwaitingChunkLoad;
	private Object partsAwaitingChunkLoadMutex;
	private Object orphanedPartsMutex;

	public TransportWorldRegistry(World world) {
		worldObj = world;
		systems = new HashSet<ITransportSystem>();
		deadSystem = new HashSet<ITransportSystem>();
		dirtySystems = new HashSet<ITransportSystem>();
		detachedParts = new HashSet<ITransportPart>();
		orphanedParts = new HashSet<ITransportPart>();
		partsAwaitingChunkLoad = new HashMap<Long, Set<ITransportPart>>();
		partsAwaitingChunkLoadMutex = new Object();
		orphanedPartsMutex = new Object();
	}

	public void tickStart() {
		if (systems.size() > 0) {
			for(ITransportSystem system : systems) {
				if (system.getWorldObj() == worldObj && system.getWorldObj().isRemote == worldObj.isRemote) {
					if (system.isEmpty()) {
						deadSystem.add(system);
					}
				}
			}
		}
	}

	public void processMultiblockChanges() {
		IChunkProvider chunkProvider = worldObj.getChunkProvider();
		BlockPos coord;
		List<Set<ITransportSystem>> mergePools = null;
		if (orphanedParts.size() > 0) {
			Set<ITransportPart> orphansToProcess = null;
			synchronized (orphanedPartsMutex) {
				if (orphanedParts.size() > 0) {
					orphansToProcess = orphanedParts;
					orphanedParts = new HashSet<ITransportPart>();
				}
			}
			if (orphansToProcess != null && orphansToProcess.size() > 0) {
				Set<ITransportSystem> compatibleSystems;
				for(ITransportPart orphan : orphansToProcess) {
					coord = orphan.getWorldLocation();
					if (!chunkProvider.chunkExists(coord.getChunkX(), coord.getChunkZ())) {
						continue;
					}
					if (orphan.getTileEntity().isInvalid()) {
						continue;
					}
					if (worldObj.getTileEntity(coord.x, coord.y, coord.z) != orphan.getTileEntity()) {
						continue;
					}
					compatibleSystems = orphan.attachToNeighbors();
					if (compatibleSystems == null) {
						ITransportSystem newSystem = orphan.createNewSystem();
						newSystem.attachPart(orphan);
						this.systems.add(newSystem);
					} else if (compatibleSystems.size() > 1) {
						if (mergePools == null) {
							mergePools = new ArrayList<Set<ITransportSystem>>();
						}
						boolean hasAddedToPool = false;
						List<Set<ITransportSystem>> candidatePools = new ArrayList<Set<ITransportSystem>>();
						for(Set<ITransportSystem> candidatePool : mergePools) {
							if (!Collections.disjoint(candidatePool, compatibleSystems)) {
								candidatePools.add(candidatePool);
							}
						}
						if (candidatePools.size() <= 0) {
							mergePools.add(compatibleSystems);
						} else if (candidatePools.size() == 1) {
							candidatePools.get(0).addAll(compatibleSystems);
						} else {
							Set<ITransportSystem> masterPool = candidatePools.get(0);
							Set<ITransportSystem> consumedPool;
							for(int i = 1; i < candidatePools.size(); i++) {
								consumedPool = candidatePools.get(i);
								masterPool.addAll(consumedPool);
								mergePools.remove(consumedPool);
							}
							masterPool.addAll(compatibleSystems);
						}
					}
				}
			}
		}
		if (mergePools != null && mergePools.size() > 0) {
			for(Set<ITransportSystem> mergePool : mergePools) {
				ITransportSystem newMaster = null;
				for(ITransportSystem system : mergePool) {
					if (newMaster == null || system.shouldConsume(newMaster)) {
						newMaster = system;
					}
				}
				if (newMaster == null) {
					Log.fatal("Multiblock system checked a merge pool of size %d, found no master candidates. This should never happen.", mergePool.size());
				} else {
					addDirtySystem(newMaster);
					for(ITransportSystem system : mergePool) {
						if (system != newMaster) {
							addDeadSystem(system);
							addDirtySystem(newMaster);
						}
					}
				}
			}
		}
		if (dirtySystems.size() > 0) {
			Set<ITransportPart> newlyDetachedParts = null;
			for(ITransportSystem system : dirtySystems) {
				newlyDetachedParts = system.checkForDisconnections();
				if (system.isEmpty()) {
					addDeadSystem(system);
				}
				if (newlyDetachedParts != null && newlyDetachedParts.size() > 0) {
					detachedParts.addAll(newlyDetachedParts);
				}
			}
			dirtySystems.clear();
		}
		if (deadSystem.size() > 0) {
			for(ITransportSystem system : deadSystem) {
				if (!system.isEmpty()) {
					Log.fatal("Found a non-empty system. Forcing it to shed its blocks and die. This should never happen!");
					detachedParts.addAll(system.detachAllBlocks());
				}
				this.systems.remove(system);
			}
			deadSystem.clear();
		}
		for(ITransportPart part : detachedParts) {
			part.assertDetached();
		}
		addAllOrphanedPartsThreadsafe(detachedParts);
		detachedParts.clear();
	}

	public void onPartAdded(ITransportPart part) {
		BlockPos worldLocation = part.getWorldLocation();
		if (!worldObj.getChunkProvider().chunkExists(worldLocation.getChunkX(), worldLocation.getChunkZ())) {
			Set<ITransportPart> partSet;
			long chunkHash = worldLocation.getChunkXZHash();
			synchronized (partsAwaitingChunkLoadMutex) {
				if (!partsAwaitingChunkLoad.containsKey(chunkHash)) {
					partSet = new HashSet<ITransportPart>();
					partsAwaitingChunkLoad.put(chunkHash, partSet);
				} else {
					partSet = partsAwaitingChunkLoad.get(chunkHash);
				}
				partSet.add(part);
			}
		} else {
			addOrphanedPartThreadsafe(part);
		}
	}

	public void onPartRemovedFromWorld(ITransportPart part) {
		BlockPos worldLocation = part.getWorldLocation();
		if (worldLocation != null) {
			long hash = worldLocation.getChunkXZHash();
			if (partsAwaitingChunkLoad.containsKey(hash)) {
				synchronized (partsAwaitingChunkLoadMutex) {
					if (partsAwaitingChunkLoad.containsKey(hash)) {
						partsAwaitingChunkLoad.get(hash).remove(part);
						if (partsAwaitingChunkLoad.get(hash).size() <= 0) {
							partsAwaitingChunkLoad.remove(hash);
						}
					}
				}
			}
		}
		detachedParts.remove(part);
		if (orphanedParts.contains(part)) {
			synchronized (orphanedPartsMutex) {
				orphanedParts.remove(part);
			}
		}
		part.assertDetached();
	}

	public void onWorldUnloaded() {
		systems.clear();
		deadSystem.clear();
		dirtySystems.clear();
		detachedParts.clear();
		synchronized (partsAwaitingChunkLoadMutex) {
			partsAwaitingChunkLoad.clear();
		}
		synchronized (orphanedPartsMutex) {
			orphanedParts.clear();
		}
		worldObj = null;
	}

	public void onChunkLoaded(int chunkX, int chunkZ) {
		long chunkHash = ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ);
		if (partsAwaitingChunkLoad.containsKey(chunkHash)) {
			synchronized (partsAwaitingChunkLoadMutex) {
				if (partsAwaitingChunkLoad.containsKey(chunkHash)) {
					addAllOrphanedPartsThreadsafe(partsAwaitingChunkLoad.get(chunkHash));
					partsAwaitingChunkLoad.remove(chunkHash);
				}
			}
		}
	}

	public void addDeadSystem(ITransportSystem deadSystem) {
		this.deadSystem.add(deadSystem);
	}

	public void addDirtySystem(ITransportSystem dirtySystem) {
		this.dirtySystems.add(dirtySystem);
	}

	public Set<ITransportSystem> getSystems() {
		return Collections.unmodifiableSet(systems);
	}

	private void addOrphanedPartThreadsafe(ITransportPart part) {
		synchronized (orphanedPartsMutex) {
			orphanedParts.add(part);
		}
	}

	private void addAllOrphanedPartsThreadsafe(Collection<? extends ITransportPart> parts) {
		synchronized (orphanedPartsMutex) {
			orphanedParts.addAll(parts);
		}
	}

	private String clientOrServer() {
		return worldObj.isRemote ? "CLIENT" : "SERVER";
	}
}
