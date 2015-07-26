package nedelosk.modularmachines.common.events;

import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.client.gui.GuiModuleRegisterError;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ModularEvents {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onOpenGui(GuiOpenEvent event)
	{
		if(event.gui instanceof GuiMainMenu)
			if(ModularMachinesApi.getRegisterFailed() != null)
				event.gui = new GuiModuleRegisterError();
	}
	
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
				event.toolTip.add(StatCollector.translateToLocal("mm.module.tooltip.name") + ": " + ModularMachinesApi.getModuleItem(event.itemStack).getModuleName());
				if(event.toolTip.size() != 3)
					event.toolTip.add(EnumChatFormatting.WHITE +  "------------------------");
				
			}
		}
	}
	
}
