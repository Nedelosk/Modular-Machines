package modularmachines.common.inventory;

import javax.annotation.Nullable;

import modularmachines.common.utils.ItemUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class ItemHandlerInvManipulator implements Iterable<InvSlot> {

	private final IItemHandler inv;

	public ItemHandlerInvManipulator(IItemHandler inv) {
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
	 * @return thse remainder
	 */
	@Nullable
	public ItemStack addStack(ItemStack stack) {
		return addStack(stack, true);
	}

	public boolean transferOneStack(IItemHandler dest, Predicate<ItemStack> filter) {
		return transferStacks(dest, filter, true);
	}

	public boolean transferStacks(IItemHandler dest, Predicate<ItemStack> filter) {
		return transferStacks(dest, filter, false);
	}

	private boolean transferStacks(IItemHandler dest, Predicate<ItemStack> filter, boolean singleStack) {
		ItemHandlerInvManipulator destManipulator = new ItemHandlerInvManipulator(dest);
		boolean stacksMoved = false;
		for (int slotIndex = 0; slotIndex < inv.getSlots(); slotIndex++) {
			ItemStack targetStack = inv.extractItem(slotIndex, Integer.MAX_VALUE, true);
			if (!targetStack.isEmpty() && filter.test(targetStack)) {
				int extractStackSize = targetStack.getCount();
				ItemStack remaining = destManipulator.tryAddStack(targetStack);
				if (remaining != null) {
					extractStackSize -= remaining.getCount();
				}
				if (extractStackSize > 0) {
					ItemStack extracted = inv.extractItem(slotIndex, extractStackSize, false);
					destManipulator.addStack(extracted);
					stacksMoved = true;
					if (singleStack) {
						return true;
					}
				}
			}
		}
		return stacksMoved;
	}

	@Nullable
	protected ItemStack addStack(ItemStack stack, boolean doAdd) {
		if (ItemUtil.isEmpty(stack)) {
			return null;
		}
		stack = stack.copy();
		List<InvSlot> filledSlots = new ArrayList<>(inv.getSlots());
		List<InvSlot> emptySlots = new ArrayList<>(inv.getSlots());
		for (InvSlot slot : new ItemHandlerInvManipulator(inv)) {
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