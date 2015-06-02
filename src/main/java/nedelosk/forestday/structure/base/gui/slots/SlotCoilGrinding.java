package nedelosk.forestday.structure.base.gui.slots;

import nedelosk.forestday.structure.base.items.ItemCoilGrinding;
import nedelosk.forestday.structure.base.items.ItemCoilHeat;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotCoilGrinding extends Slot {

	public SlotCoilGrinding(IInventory inventory, int id, int x, int z) {
		super(inventory, id, x, z);
	}
	
	@Override
	public int getSlotStackLimit() {
		return 1;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return stack.getItem() instanceof ItemCoilGrinding;
	}

}
