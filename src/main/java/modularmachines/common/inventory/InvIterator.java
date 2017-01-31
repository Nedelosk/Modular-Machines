package modularmachines.common.inventory;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.minecraftforge.items.IItemHandler;

public class InvIterator implements Iterator<InvSlot> {
	private final IItemHandler inv;
	private int slot = 0;

	public InvIterator(IItemHandler inv) {
		this.inv = inv;
	}

	@Override
	public boolean hasNext() {
		return slot < inv.getSlots();
	}

	@Override
	public InvSlot next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		return new InvSlot(inv, slot++);
	}
}