package de.nedelosk.forestmods.common.inventory.slots;

import de.nedelosk.forestmods.api.utils.ModuleManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotModularAssembler extends Slot {

	public SlotModularAssembler(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
		super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return ModuleManager.moduleRegistry.getModuleFromItem(stack) != null;
	}
}
