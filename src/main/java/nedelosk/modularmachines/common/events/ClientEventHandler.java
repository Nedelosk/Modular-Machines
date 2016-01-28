package nedelosk.modularmachines.common.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.utils.ModuleRegistry;
import nedelosk.modularmachines.api.utils.ModuleRegistry.ModuleItem;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class ClientEventHandler {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void tooltipEvent(ItemTooltipEvent event) {
		ModuleItem moduleItem = ModuleRegistry.getModuleFromItem(event.itemStack);
		if (moduleItem != null) {
			event.toolTip.add(StatCollector.translateToLocal("mm.module.tooltip.type") + ": " + moduleItem.material.getLocalName());
			event.toolTip.add(StatCollector.translateToLocal("mm.module.tooltip.tier") + ": " + moduleItem.material.getTier());
			event.toolTip.add(StatCollector.translateToLocal("mm.module.tooltip.name") + ": "
					+ StatCollector.translateToLocal(moduleItem.moduleStack.getModule().getName(moduleItem.moduleStack) + ".name"));
		}
	}
}
