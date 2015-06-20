package nedelosk.nedeloskcore.common.inventory.slots;

import nedelosk.nedeloskcore.common.items.ItemPlan;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotPlan extends Slot {

	public SlotPlan(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
		super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return stack.getItem() instanceof ItemPlan && stack.getItemDamage() == 0;
	}

}
