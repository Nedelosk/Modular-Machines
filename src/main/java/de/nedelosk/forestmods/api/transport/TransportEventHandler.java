package de.nedelosk.forestmods.api.transport;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;

public class TransportEventHandler {

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onChunkLoad(ChunkEvent.Load loadEvent) {
		Chunk chunk = loadEvent.getChunk();
		World world = loadEvent.world;
		TransportRegistry.onChunkLoaded(world, chunk.xPosition, chunk.zPosition);
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onWorldUnload(WorldEvent.Unload unloadWorldEvent) {
		TransportRegistry.onWorldUnloaded(unloadWorldEvent.world);
	}
}
