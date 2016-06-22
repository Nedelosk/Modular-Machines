package de.nedelosk.modularmachines.common.transport;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TransportServerTickHandler {

	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event) {
		if (event.phase == TickEvent.Phase.START) {
			TransportRegistry.tickStart(event.world);
		}
	}
}
