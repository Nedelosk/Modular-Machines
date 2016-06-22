package de.nedelosk.modularmachines.common.transport;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TransportEventHandler {

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onChunkLoad(ChunkEvent.Load loadEvent) {
		Chunk chunk = loadEvent.getChunk();
		World world = loadEvent.getWorld();
		TransportRegistry.onChunkLoaded(world, chunk.xPosition, chunk.zPosition);
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onWorldUnload(WorldEvent.Unload unloadWorldEvent) {
		TransportRegistry.onWorldUnloaded(unloadWorldEvent.getWorld());
	}
}
