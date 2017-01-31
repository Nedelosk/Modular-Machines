package modularmachines.common.inventory;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class InvSlot {

	private final IItemHandler inv;
	protected final int slot;

	public InvSlot(IItemHandler inv, int slot) {
		this.inv = inv;
		this.slot = slot;
	}

	public int getIndex() {
		return slot;
	}

	public boolean canPutStackInSlot(ItemStack stack) {
		ItemStack remainder = inv.insertItem(slot, stack, true);
		return remainder.isEmpty() || remainder.getCount() < stack.getCount();
	}

	public ItemStack getStackInSlot() {
		return inv.getStackInSlot(slot);
	}

}