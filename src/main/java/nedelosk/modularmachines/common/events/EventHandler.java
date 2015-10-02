package nedelosk.modularmachines.common.events;

import nedelosk.modularmachines.api.modular.module.utils.ModularManager;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EventHandler {
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void tooltipEvent(ItemTooltipEvent event)
	{
		if(ModularManager.getModuleStack(event.itemStack) != null)
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
				event.toolTip.add(StatCollector.translateToLocal("mm.module.tooltip.tier") + ": " + ModularManager.getModuleStack(event.itemStack).getTier());
				event.toolTip.add(StatCollector.translateToLocal("mm.module.tooltip.name") + ": " + StatCollector.translateToLocal(ModularManager.getModuleStack(event.itemStack).getModuleName() + ".name"));
				if(event.toolTip.size() != 3)
					event.toolTip.add(EnumChatFormatting.WHITE +  "------------------------");
				
			}
		}
	}
	
}
