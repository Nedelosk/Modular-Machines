package nedelosk.forestday.common.machines.base.slots;

import nedelosk.forestday.common.items.materials.ItemCampfire;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCampfire extends Slot {

	public String slotKey;
	
	public SlotCampfire(IInventory inventory, int ID, int x, int y, String slotKey) {
		super(inventory, ID, x, y);
		this.slotKey = slotKey;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return stack.getItem() instanceof ItemCampfire && ((ItemCampfire)stack.getItem()).itemName == slotKey;
	}
	
	@Override
	public int getSlotStackLimit() {
		return 1;
	}

}
