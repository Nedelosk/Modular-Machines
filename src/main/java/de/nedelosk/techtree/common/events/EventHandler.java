package de.nedelosk.techtree.common.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.techtree.api.TechTreePlayerData;
import de.nedelosk.techtree.client.proxy.ClientProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.entity.EntityEvent;

public class EventHandler {

	@SubscribeEvent
	public void onPlayer(EntityEvent.EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			if (event.entity.getExtendedProperties("TECHTREE") == null) {
				event.entity.registerExtendedProperties("TECHTREE", new TechTreePlayerData((EntityPlayer) event.entity));
			}
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onRenderGameOverlay(RenderGameOverlayEvent event) {
		if (event.type == ElementType.ALL && !event.isCancelable()) {
			if (ClientProxy.techPointGui != null) {
				ClientProxy.techPointGui.drawTechPointTab(event.resolution.getScaledWidth() - 160, event.resolution.getScaledHeight() - 255);
			}
		}
	}
}
