package nedelosk.modularmachines.common.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import nedelosk.modularmachines.client.proxy.ClientProxy;
import nedelosk.modularmachines.common.ModularMachines;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.modularmachines.common.network.packets.techtree.PacketSyncDataClient;
import net.minecraft.client.Minecraft;

public class KeyHandler {

	@SubscribeEvent
	public void keyBindEvent(KeyInputEvent event)
	{
		if(ClientProxy.techTree.isPressed())
		{
			PacketHandler.INSTANCE.sendToServer(new PacketSyncDataClient());
			FMLNetworkHandler.openGui(Minecraft.getMinecraft().thePlayer, ModularMachines.instance, 2, Minecraft.getMinecraft().theWorld, 0, 0, 0);
		}
		if(ClientProxy.techTreeEditor.isPressed())
		{
			FMLNetworkHandler.openGui(Minecraft.getMinecraft().thePlayer, ModularMachines.instance, 3, Minecraft.getMinecraft().theWorld, 0, 0, 0);
		}
	}
	
}
