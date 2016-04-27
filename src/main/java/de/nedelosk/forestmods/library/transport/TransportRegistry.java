package de.nedelosk.forestmods.library.transport;

import java.util.HashMap;
import java.util.Set;

import de.nedelosk.forestmods.library.utils.Log;
import net.minecraft.world.World;

public class TransportRegistry {

	private static HashMap<World, TransportWorldRegistry> registries = new HashMap<World, TransportWorldRegistry>();

	public static void tickStart(World world) {
		if (registries.containsKey(world)) {
			TransportWorldRegistry registry = registries.get(world);
			registry.processMultiblockChanges();
			registry.tickStart();
		}
	}

	public static void onChunkLoaded(World world, int chunkX, int chunkZ) {
		if (registries.containsKey(world)) {
			registries.get(world).onChunkLoaded(chunkX, chunkZ);
		}
	}

	public static void onPartAdded(World world, ITransportPart part) {
		TransportWorldRegistry registry = getOrCreateRegistry(world);
		registry.onPartAdded(part);
	}

	public static void onPartRemovedFromWorld(World world, ITransportPart part) {
		if (registries.containsKey(world)) {
			registries.get(world).onPartRemovedFromWorld(part);
		}
	}

	public static void onWorldUnloaded(World world) {
		if (registries.containsKey(world)) {
			registries.get(world).onWorldUnloaded();
			registries.remove(world);
		}
	}

	public static void addDirtySystem(World world, ITransportSystem system) {
		if (registries.containsKey(world)) {
			registries.get(world).addDirtySystem(system);
		} else {
			throw new IllegalArgumentException("Adding a dirty system to a world that has no registered systems!");
		}
	}

	public static void addDeadSystem(World world, ITransportSystem system) {
		if (registries.containsKey(world)) {
			registries.get(world).addDeadSystem(system);
		} else {
			Log.warn("System %d in world %s marked as dead, but that world is not tracked! System is being ignored.", system.hashCode(), world);
		}
	}

	public static Set<ITransportSystem> getSystemsFromWorld(World world) {
		if (registries.containsKey(world)) {
			return registries.get(world).getSystems();
		}
		return null;
	}

	/// *** PRIVATE HELPERS *** ///
	private static TransportWorldRegistry getOrCreateRegistry(World world) {
		if (registries.containsKey(world)) {
			return registries.get(world);
		} else {
			TransportWorldRegistry newRegistry = new TransportWorldRegistry(world);
			registries.put(world, newRegistry);
			return newRegistry;
		}
	}
}
