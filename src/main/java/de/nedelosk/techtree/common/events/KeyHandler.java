package de.nedelosk.techtree.common.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import de.nedelosk.techtree.client.proxy.ClientProxy;
import de.nedelosk.techtree.common.TechTree;
import de.nedelosk.techtree.common.network.packets.PacketHandler;
import de.nedelosk.techtree.common.network.packets.PacketSyncDataClient;
import net.minecraft.client.Minecraft;

public class KeyHandler {

	@SubscribeEvent
	public void keyBindEvent(KeyInputEvent event) {
		if (ClientProxy.techTree.isPressed()) {
			PacketHandler.INSTANCE.sendToServer(new PacketSyncDataClient());
			FMLNetworkHandler.openGui(Minecraft.getMinecraft().thePlayer, TechTree.instance, 0, Minecraft.getMinecraft().theWorld, 0, 0, 0);
		}
		if (ClientProxy.techTreeEditor.isPressed()) {
			FMLNetworkHandler.openGui(Minecraft.getMinecraft().thePlayer, TechTree.instance, 1, Minecraft.getMinecraft().theWorld, 0, 0, 0);
		}
	}
}
