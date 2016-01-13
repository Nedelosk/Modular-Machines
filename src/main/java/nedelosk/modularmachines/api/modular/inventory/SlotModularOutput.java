package nedelosk.modularmachines.api.modular.inventory;

import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotModularOutput extends SlotModular {

	public SlotModularOutput(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_, ModuleStack stack) {
		super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_, stack);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}
}
