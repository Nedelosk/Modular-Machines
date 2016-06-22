package de.nedelosk.modularmachines.api.modules.handlers.inventory.slots;

import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotModule extends SlotItemHandler {

	public IModuleState module;

	public SlotModule(IModuleState moduleState, int index, int xPosition, int yPosition) {
		super((IItemHandler) moduleState.getContentHandler(ItemStack.class), index, xPosition, yPosition);
		this.module = moduleState;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		if(super.isItemValid(stack)){
			IModuleInventory inventory = (IModuleInventory) module.getContentHandler(ItemStack.class);
			if (inventory.isInput(getSlotIndex())) {
				return inventory.getInsertFilter().isValid(getSlotIndex(), stack, module);
			}
		}
		return false;
	}
}