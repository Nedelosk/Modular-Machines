package modularmachines.common.inventory;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.item.ItemStack;

import net.minecraftforge.items.IItemHandler;

import modularmachines.common.utils.ItemUtil;

public class InventoryManipulator implements Iterable<InvSlot> {
	
	private final IItemHandler inv;
	
	public InventoryManipulator(IItemHandler inv) {
		this.inv = inv;
	}
	
	@Override
	public Iterator<InvSlot> iterator() {
		return new InvIterator(inv);
	}
	
	/**
	 * Simulate adding the stack to the inventory.
	 *
	 * @return the remainder
	 */
	@Nullable
	public ItemStack tryAddStack(ItemStack stack) {
		return addStack(stack, false);
	}
	
	/**
	 * Attempt to add the stack to the inventory.
	 *
	 * @return the remainder
	 */
	@Nullable
	public ItemStack addStack(ItemStack stack) {
		return addStack(stack, true);
	}
	
	@Nullable
	public ItemStack addStack(ItemStack stack, boolean doAdd) {
		if (ItemUtil.isEmpty(stack)) {
			return null;
		}
		stack = stack.copy();
		List<InvSlot> filledSlots = new ArrayList<>(inv.getSlots());
		List<InvSlot> emptySlots = new ArrayList<>(inv.getSlots());
		for (InvSlot slot : new InventoryManipulator(inv)) {
			if (slot.canPutStackInSlot(stack)) {
				if (ItemUtil.isEmpty(slot.getStackInSlot())) {
					emptySlots.add(slot);
				} else {
					filledSlots.add(slot);
				}
			}
		}
		
		int injected = 0;
		injected = tryPut(filledSlots, stack, injected, doAdd);
		injected = tryPut(emptySlots, stack, injected, doAdd);
		ItemUtil.shrink(stack, injected);
		return stack;
	}
	
	private int tryPut(List<InvSlot> slots, ItemStack stack, int injected, boolean doAdd) {
		if (injected >= ItemUtil.getCount(stack)) {
			return injected;
		}
		for (InvSlot slot : slots) {
			final ItemStack stackToInsert = stack.copy();
			final int stackToInsertSize = ItemUtil.getCount(stack) - injected;
			ItemUtil.setCount(stackToInsert, stackToInsertSize);
			
			final ItemStack remainder = inv.insertItem(slot.getIndex(), stackToInsert, !doAdd);
			if (ItemUtil.isEmpty(remainder)) {
				return ItemUtil.getCount(stack);
			}
			injected += stackToInsertSize - ItemUtil.getCount(remainder);
			if (injected >= ItemUtil.getCount(stack)) {
				return injected;
			}
		}
		return injected;
	}
}