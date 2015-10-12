package nedelosk.modularmachines.common.events;

import nedelosk.modularmachines.api.modular.module.basic.basic.IModuleWithItem;
import nedelosk.modularmachines.api.modular.module.producer.producer.IModuleProducer;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry.ModuleRegisterEvent;
import nedelosk.modularmachines.common.core.registry.ItemRegistry;
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
		if(ModuleRegistry.getModuleStack(event.itemStack) != null)
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
				event.toolTip.add(StatCollector.translateToLocal("mm.module.tooltip.tier") + ": " + ModuleRegistry.getModuleStack(event.itemStack).getTier());
				event.toolTip.add(StatCollector.translateToLocal("mm.module.tooltip.name") + ": " + StatCollector.translateToLocal(ModuleRegistry.getModuleStack(event.itemStack).getModuleName() + ".name"));
				if(event.toolTip.size() != 3)
					event.toolTip.add(EnumChatFormatting.WHITE +  "------------------------");
				
			}
		}
	}
	
	@SubscribeEvent
	public void onRegisterModule(ModuleRegisterEvent event){
		if(event.module instanceof IModuleWithItem)
			ItemRegistry.Modules.addModule(event.module.getName());
	}
	
}
