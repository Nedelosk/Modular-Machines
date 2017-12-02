package modularmachines.common.core;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import modularmachines.api.modules.IModuleData;
import modularmachines.api.modules.IModuleType;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.positions.CasingPosition;

public class EventHandler {
	
	@SubscribeEvent
	public void onUseItem(PlayerInteractEvent.RightClickBlock event) {
		ItemStack itemStack = event.getItemStack();
		if (!itemStack.isEmpty()) {
			IModuleType type = ModuleManager.registry.getTypeFromItem(itemStack);
			if (type == null) {
				return;
			}
			IModuleData moduleData = type.getData();
			if (!moduleData.isValidPosition(CasingPosition.CENTER)) {
				return;
			}
			if (ModuleManager.registry.placeModule(event.getWorld(), event.getPos(), event.getEntityPlayer(), event.getHand(), event.getFace())) {
				event.setCancellationResult(EnumActionResult.SUCCESS);
				event.setCanceled(true);
			}
		}
	}
}
