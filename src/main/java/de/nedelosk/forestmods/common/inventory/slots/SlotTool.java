package de.nedelosk.forestmods.common.inventory.slots;

import de.nedelosk.forestmods.common.items.ItemToolForestday;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotTool extends Slot {

	public SlotTool(IInventory arg0, int arg1, int arg2, int arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		if (stack.getItem() instanceof ItemToolForestday) {
			return true;
		}
		return false;
	}
}
