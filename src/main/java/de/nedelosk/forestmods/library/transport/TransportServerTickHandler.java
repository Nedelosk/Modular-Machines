package de.nedelosk.forestmods.library.transport;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class TransportServerTickHandler {

	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event) {
		if (event.phase == TickEvent.Phase.START) {
			TransportRegistry.tickStart(event.world);
		}
	}
}
