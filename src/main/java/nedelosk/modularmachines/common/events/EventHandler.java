package nedelosk.modularmachines.common.events;

import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.basic.techtree.TechTreePlayerData;
import nedelosk.modularmachines.client.gui.GuiModuleRegisterError;
import nedelosk.modularmachines.client.proxy.ClientProxy;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EventHandler {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onOpenGui(GuiOpenEvent event)
	{
		if(event.gui instanceof GuiMainMenu)
			if(ModularMachinesApi.getRegisterFailed() != null)
				event.gui = new GuiModuleRegisterError();
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void tooltipEvent(ItemTooltipEvent event)
	{
		if(ModularMachinesApi.getModuleItem(event.itemStack) != null)
		{
			if(!GuiScreen.isShiftKeyDown())
			{
				if(!event.toolTip.contains(EnumChatFormatting.WHITE + (EnumChatFormatting.ITALIC + "<" + StatCollector.translateToLocal("mm.module.tooltip.shift") + ">")))
					event.toolTip.add(EnumChatFormatting.WHITE + (EnumChatFormatting.ITALIC + "<" + StatCollector.translateToLocal("mm.module.tooltip.shift") + ">"));
			}
			else
			{
				if(event.toolTip.size() != 1)
					event.toolTip.add(EnumChatFormatting.WHITE +  "------------------------");
				event.toolTip.add(StatCollector.translateToLocal("mm.module.tooltip.tier") + ": " + ModularMachinesApi.getModuleItem(event.itemStack).getTier());
				event.toolTip.add(StatCollector.translateToLocal("mm.module.tooltip.name") + ": " + StatCollector.translateToLocal(ModularMachinesApi.getModuleItem(event.itemStack).getModuleName() + ".name"));
				if(event.toolTip.size() != 3)
					event.toolTip.add(EnumChatFormatting.WHITE +  "------------------------");
				
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayer(EntityEvent.EntityConstructing event)
	{
		if(event.entity instanceof EntityPlayer)
		{
			if(event.entity.getExtendedProperties("MODULARMACHINES:TECHTREE") == null)
				event.entity.registerExtendedProperties("MODULARMACHINES:TECHTREE", new TechTreePlayerData((EntityPlayer)event.entity));
		}
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onRenderGameOverlay(RenderGameOverlayEvent event)
	{
		if(event.type == ElementType.ALL && !event.isCancelable())
		{
			if(ClientProxy.techPointGui != null)
				ClientProxy.techPointGui.drawTechPointTab(event.resolution.getScaledWidth() - 160, event.resolution.getScaledHeight() - 255);
		}
	}
	
}
