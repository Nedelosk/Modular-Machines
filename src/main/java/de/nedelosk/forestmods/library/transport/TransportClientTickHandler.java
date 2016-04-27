package de.nedelosk.forestmods.library.transport;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;

public class TransportClientTickHandler {

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.START) {
			TransportRegistry.tickStart(Minecraft.getMinecraft().theWorld);
		}
	}
}
