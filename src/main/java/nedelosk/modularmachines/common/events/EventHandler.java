package nedelosk.modularmachines.common.events;

import nedelosk.modularmachines.api.utils.ModuleRegistry;
import nedelosk.modularmachines.api.utils.ModuleStack;
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
	public void tooltipEvent(ItemTooltipEvent event) {
		ModuleStack stack = ModuleRegistry.getModuleItem(event.itemStack);
		if (stack != null) {
			if (!GuiScreen.isShiftKeyDown()) {
				if (!event.toolTip.contains(EnumChatFormatting.WHITE + (EnumChatFormatting.ITALIC + "<"
						+ StatCollector.translateToLocal("mm.module.tooltip.shift") + ">")))
					event.toolTip.add(EnumChatFormatting.WHITE + (EnumChatFormatting.ITALIC + "<"
							+ StatCollector.translateToLocal("mm.module.tooltip.shift") + ">"));
			} else {
				if (event.toolTip.size() != 1)
					event.toolTip.add(EnumChatFormatting.WHITE + "------------------------");
				event.toolTip.add(StatCollector.translateToLocal("mm.module.tooltip.type") + ": "
						+ stack.getType().getLocalName());
				event.toolTip.add(StatCollector.translateToLocal("mm.module.tooltip.tier") + ": "
						+ ModuleRegistry.getModuleItem(event.itemStack).getType().getTier());
				event.toolTip.add(StatCollector.translateToLocal("mm.module.tooltip.name") + ": "
						+ StatCollector.translateToLocal(stack.getModule().getName(stack, false) + ".name"));
				if (stack.getProducer() != null) {
					event.toolTip.add(StatCollector.translateToLocal("mm.module.tooltip.producer.name") + ": "
							+ StatCollector.translateToLocal(stack.getProducer().getName(stack) + ".name"));
					if (event.toolTip.size() != 5)
						event.toolTip.add(EnumChatFormatting.WHITE + "------------------------");
				} else {
					if (event.toolTip.size() != 4)
						event.toolTip.add(EnumChatFormatting.WHITE + "------------------------");
				}
			}
		}
	}

}
