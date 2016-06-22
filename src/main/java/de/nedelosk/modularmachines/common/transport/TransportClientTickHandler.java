package de.nedelosk.modularmachines.common.transport;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TransportClientTickHandler {

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.START) {
			TransportRegistry.tickStart(Minecraft.getMinecraft().theWorld);
		}
	}
}
