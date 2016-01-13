package nedelosk.modularmachines.common.inventory.slots;

import nedelosk.modularmachines.common.crafting.CokeOvenRecipeManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCokeOven extends Slot {

	public SlotCokeOven(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
		super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return CokeOvenRecipeManager.isItemInput(stack);
	}
}
