package modularmachines.common.inventory;

import java.util.Iterator;
import java.util.NoSuchElementException;

import modularmachines.common.utils.ItemUtil;
import net.minecraftforge.items.IItemHandler;

public class InvIterator implements Iterator<InvSlot> {
	private final IItemHandler inv;
	private final int[] slots;
	private int slot = 0;

	public InvIterator(IItemHandler inv) {
		this.inv = inv;
		this.slots = ItemUtil.getSlots(inv);
	}
	
	public InvIterator(IItemHandler inv, int[] slots) {
		this.inv = inv;
		this.slots = slots;
	}

	@Override
	public boolean hasNext() {
		return slot < slots.length;
	}

	@Override
	public InvSlot next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		return new InvSlot(inv, slots[slot++]);
	}
}