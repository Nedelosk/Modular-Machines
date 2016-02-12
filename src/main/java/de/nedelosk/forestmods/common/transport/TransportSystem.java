package de.nedelosk.forestmods.common.transport;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.nedelosk.forestcore.utils.BlockPos;
import de.nedelosk.forestcore.utils.Log;
import de.nedelosk.forestmods.api.transport.ITransportPart;
import de.nedelosk.forestmods.api.transport.ITransportSystem;
import de.nedelosk.forestmods.api.transport.TransportRegistry;
import de.nedelosk.forestmods.api.transport.node.IContentHandler;
import de.nedelosk.forestmods.api.transport.node.INodeSide;
import de.nedelosk.forestmods.api.transport.node.ITransportNode;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class TransportSystem implements ITransportSystem {

	public HashSet<ITransportNode> connectedNodes;
	public HashSet<ITransportPart> connectedParts;
	public World worldObj;
	private boolean shouldCheckForDisconnections;
	private BlockPos referenceCoord;

	public TransportSystem(World world) {
		worldObj = world;
		connectedParts = new HashSet<ITransportPart>();
		connectedNodes = new HashSet<ITransportNode>();
		shouldCheckForDisconnections = true;
	}

	public boolean hasBlock(BlockPos blockCoord) {
		return connectedParts.contains(blockCoord);
	}

	@Override
	public void attachPart(ITransportPart part) {
		ITransportPart candidate;
		BlockPos coord = part.getWorldLocation();
		if (!connectedParts.add(part)) {
			Log.warn(
					"[%s] System %s is double-adding part %d @ %s. This is unusual. If you encounter odd behavior, please tear down the machine and rebuild it.",
					(worldObj.isRemote ? "CLIENT" : "SERVER"), hashCode(), part.hashCode(), coord);
		}
		onPartAdded(part);
		part.onAttached(this);
		TransportRegistry.addDirtySystem(worldObj, this);
		updateNodes();
	}

	@Override
	public void detachPart(ITransportPart part, boolean chunkUnloading) {
		// Strip out this part
		onDetachPart(part);
		part.onDetached(this);
		if (!connectedParts.remove(part)) {
			Log.warn(
					"[%s] Double-removing part (%d) @ %d, %d, %d, this is unexpected and may cause problems. If you encounter anomalies, please tear down the reactor and rebuild it.",
					worldObj.isRemote ? "CLIENT" : "SERVER", part.hashCode(), part.getTileEntity().xCoord, part.getTileEntity().yCoord,
					part.getTileEntity().zCoord);
		}
		if (connectedParts.isEmpty()) {
			// Destroy/unregister
			TransportRegistry.addDeadSystem(this.worldObj, this);
			return;
		}
		TransportRegistry.addDirtySystem(this.worldObj, this);
		updateNodes();
	}

	public void onDetachPart(ITransportPart part) {
		shouldCheckForDisconnections = true;
		if (part instanceof ITransportNode) {
			connectedNodes.remove(part);
		}
	}

	public void onPartAdded(ITransportPart newPart) {
		if (newPart instanceof ITransportNode) {
			connectedNodes.add((ITransportNode) newPart);
		}
	}

	@Override
	public boolean isEmpty() {
		return connectedParts.isEmpty();
	}

	@Override
	public boolean shouldConsume(ITransportSystem otherSystem) {
		if (!otherSystem.getClass().equals(getClass())) {
			throw new IllegalArgumentException("Attempting to merge two multiblocks with different master classes - this should never happen!");
		}
		if (otherSystem == this) {
			return false;
		}
		if (otherSystem.getParts().size() > getParts().size()) {
			return false;
		} else if (otherSystem.getParts().size() < getParts().size()) {
			return true;
		}
		auditParts();
		otherSystem.auditParts();
		if (otherSystem.getParts().size() > getParts().size()) {
			return false;
		} else if (otherSystem.getParts().size() < getParts().size()) {
			return true;
		}
		return true;
	}

	@Override
	public String getPartsListString() {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for ( ITransportPart part : connectedParts ) {
			if (!first) {
				sb.append(", ");
			}
			sb.append(
					String.format("(%d: %d, %d, %d)", part.hashCode(), part.getTileEntity().xCoord, part.getTileEntity().yCoord, part.getTileEntity().zCoord));
			first = false;
		}
		return sb.toString();
	}

	@Override
	public void auditParts() {
		HashSet<ITransportPart> deadParts = new HashSet<ITransportPart>();
		for ( ITransportPart part : connectedParts ) {
			if (part.getTileEntity().isInvalid()
					|| worldObj.getTileEntity(part.getTileEntity().xCoord, part.getTileEntity().yCoord, part.getTileEntity().zCoord) != part.getTileEntity()) {
				onDetachPart(part);
				deadParts.add(part);
			}
		}
		connectedParts.removeAll(deadParts);
		Log.warn("[%s] System found %d dead parts during an audit, %d parts remain attached", worldObj.isRemote ? "CLIENT" : "SERVER", deadParts.size(),
				connectedParts.size());
	}

	public void updateNodes() {
		for ( ITransportNode node : connectedNodes ) {
			for ( INodeSide side : node.getSides() ) {
				for ( IContentHandler handler : side.getContentHandlers() ) {
					handler.update();
				}
			}
		}
	}

	@Override
	public Set<ITransportPart> checkForDisconnections() {
		if (!this.shouldCheckForDisconnections) {
			return null;
		}
		if (this.isEmpty()) {
			TransportRegistry.addDeadSystem(worldObj, this);
			return null;
		}
		TileEntity te;
		IChunkProvider chunkProvider = worldObj.getChunkProvider();
		// Invalidate our reference coord, we'll recalculate it shortly
		referenceCoord = null;
		// Reset visitations and find the minimum coordinate
		Set<ITransportPart> deadParts = new HashSet<ITransportPart>();
		BlockPos c;
		ITransportPart referencePart = null;
		int originalSize = connectedParts.size();
		for ( ITransportPart part : connectedParts ) {
			// This happens during chunk unload.
			if (!chunkProvider.chunkExists(part.getTileEntity().xCoord >> 4, part.getTileEntity().zCoord >> 4) || part.getTileEntity().isInvalid()) {
				deadParts.add(part);
				onDetachPart(part);
				continue;
			}
			if (worldObj.getTileEntity(part.getTileEntity().xCoord, part.getTileEntity().yCoord, part.getTileEntity().zCoord) != part.getTileEntity()) {
				deadParts.add(part);
				onDetachPart(part);
				continue;
			}
			part.setUnvisited();
			c = part.getWorldLocation();
			if (referenceCoord == null) {
				referenceCoord = c;
				referencePart = part;
			} else if (c.compareTo(referenceCoord) < 0) {
				referenceCoord = c;
				referencePart = part;
			}
		}
		connectedParts.removeAll(deadParts);
		deadParts.clear();
		if (referencePart == null || isEmpty()) {
			// There are no valid parts remaining. The entire multiblock was
			// unloaded during a chunk unload. Halt.
			shouldCheckForDisconnections = false;
			TransportRegistry.addDeadSystem(worldObj, this);
			return null;
		}
		// Now visit all connected parts, breadth-first, starting from reference
		// coord's part
		ITransportPart part;
		LinkedList<ITransportPart> partsToCheck = new LinkedList<ITransportPart>();
		List<ITransportPart> nearbyParts = null;
		int visitedParts = 0;
		partsToCheck.add(referencePart);
		while (!partsToCheck.isEmpty()) {
			part = partsToCheck.removeFirst();
			part.setVisited();
			visitedParts++;
			nearbyParts = part.getNeighboringParts(); // Chunk-safe on server,
														// but not on client
			for ( ITransportPart nearbyPart : nearbyParts ) {
				// Ignore different machines
				if (nearbyPart.getSystem() != this) {
					continue;
				}
				if (!nearbyPart.isVisited()) {
					nearbyPart.setVisited();
					partsToCheck.add(nearbyPart);
				}
			}
		}
		// Finally, remove all parts that remain disconnected.
		Set<ITransportPart> removedParts = new HashSet<ITransportPart>();
		for ( ITransportPart orphanCandidate : connectedParts ) {
			if (!orphanCandidate.isVisited()) {
				deadParts.add(orphanCandidate);
				orphanCandidate.getTileEntity().markDirty();
				onDetachPart(orphanCandidate);
				removedParts.add(orphanCandidate);
			}
		}
		// Trim any blocks that were invalid, or were removed.
		connectedParts.removeAll(deadParts);
		// Cleanup. Not necessary, really.
		deadParts.clear();
		// Juuuust in case.
		if (referenceCoord == null) {
			selectNewReferenceCoord();
		}
		// We've run the checks from here on out.
		shouldCheckForDisconnections = false;
		return removedParts;
	}

	@Override
	public HashSet<ITransportPart> getParts() {
		return connectedParts;
	}

	@Override
	public BlockPos getReferenceCoord() {
		if (referenceCoord == null) {
			selectNewReferenceCoord();
		}
		return referenceCoord;
	}

	private void selectNewReferenceCoord() {
		IChunkProvider chunkProvider = worldObj.getChunkProvider();
		referenceCoord = null;
		for ( ITransportPart part : connectedParts ) {
			if (part.getTileEntity().isInvalid() || !chunkProvider.chunkExists(part.getTileEntity().xCoord >> 4, part.getTileEntity().zCoord >> 4)) {
				// Chunk is unloading, skip this coord to prevent chunk
				// thrashing
				continue;
			}
			if (referenceCoord == null || referenceCoord.compareTo(part.getWorldLocation()) > 0) {
				referenceCoord = part.getWorldLocation();
			}
		}
	}

	@Override
	public Set<ITransportPart> detachAllBlocks() {
		if (worldObj == null) {
			return new HashSet<ITransportPart>();
		}
		IChunkProvider chunkProvider = worldObj.getChunkProvider();
		for ( ITransportPart part : connectedParts ) {
			if (chunkProvider.chunkExists(part.getTileEntity().xCoord >> 4, part.getTileEntity().zCoord >> 4)) {
				onDetachPart(part);
			}
		}
		Set<ITransportPart> detachedParts = connectedParts;
		connectedParts = new HashSet<ITransportPart>();
		return detachedParts;
	}

	@Override
	public World getWorldObj() {
		return worldObj;
	}

	@Override
	public HashSet<ITransportNode> getNodes() {
		return connectedNodes;
	}
}
