package nedelosk.forestday.common.machines.base.slots;

import nedelosk.forestday.module.lumberjack.items.ItemPattern;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotPattern extends Slot {
	
	public SlotPattern(IInventory arg0, int arg1, int arg2, int arg3) {
		super(arg0, arg1, arg2, arg3);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		if(stack.getItem() instanceof ItemPattern)
		{
			return true;
		}
		return false;
	}

}
