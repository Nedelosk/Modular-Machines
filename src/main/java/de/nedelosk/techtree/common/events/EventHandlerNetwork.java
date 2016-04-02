package de.nedelosk.techtree.common.events;

import java.util.ArrayList;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.techtree.api.TechPointStack;
import de.nedelosk.techtree.api.TechTreeApi;
import de.nedelosk.techtree.api.TechTreeManager;
import de.nedelosk.techtree.api.TechTreePlayerData;
import de.nedelosk.techtree.client.proxy.ClientProxy;
import de.nedelosk.techtree.common.network.packets.PacketHandler;
import de.nedelosk.techtree.common.network.packets.PacketSyncData;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class EventHandlerNetwork {

	@SubscribeEvent
	public void playerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if (side == Side.SERVER) {
			PacketHandler.INSTANCE.sendTo(new PacketSyncData(event.player), (EntityPlayerMP) event.player);
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void clientLoggedIn(FMLNetworkEvent.ClientConnectedToServerEvent event) {
		if (Minecraft.getMinecraft().thePlayer != null) {
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			ArrayList<String> listEntrys = new ArrayList();
			if (player.getExtendedProperties("MODULARMACHINES:TECHTREE") == null) {
				player.registerExtendedProperties("MODULARMACHINES:TECHTREE", new TechTreePlayerData(player));
			} else {
				listEntrys = ((TechTreePlayerData) player.getExtendedProperties("MODULARMACHINES:TECHTREE")).techEntrys;
			}
		}
	}

	@SubscribeEvent
	public void onItemCrafting(ItemCraftedEvent event) {
		if (event.crafting != null) {
			if (TechTreeApi.getTechPointsFromItem(event.crafting) != null) {
				TechPointStack[] stacks = TechTreeApi.getTechPointsFromItem(event.crafting);
				for ( TechPointStack stack : stacks ) {
					TechTreeManager.addTechPoints(event.player, stack.points, stack.type);
					if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
						ClientProxy.techPointGui.addPoints(stack.type, stack.points);
					}
				}
				((TechTreePlayerData) event.player.getExtendedProperties("MODULARMACHINES:TECHTREE")).craftetTechPoints.add(event.crafting);
			}
		}
	}
}
