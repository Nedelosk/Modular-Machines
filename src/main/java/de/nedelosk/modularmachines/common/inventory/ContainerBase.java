package de.nedelosk.modularmachines.common.inventory;

import java.util.List;

import de.nedelosk.modularmachines.api.gui.IContainerBase;
import de.nedelosk.modularmachines.api.gui.IGuiHandler;
import de.nedelosk.modularmachines.common.utils.ItemUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

public abstract class ContainerBase<H extends IGuiHandler> extends Container implements IContainerBase<H> {

	protected H handler;
	protected EntityPlayer player;

	public ContainerBase(H tile, InventoryPlayer inventory) {
		this.handler = tile;
		this.player = inventory.player;
		addInventory(inventory);
		addSlots(inventory);
	}

	protected void addInventory(InventoryPlayer inventory) {
		for(int i1 = 0; i1 < 3; i1++) {
			for(int l1 = 0; l1 < 9; l1++) {
				addSlotToContainer(new Slot(inventory, l1 + i1 * 9 + 9, 8 + l1 * 18, 84 + i1 * 18));
			}
		}
		for(int j1 = 0; j1 < 9; j1++) {
			addSlotToContainer(new Slot(inventory, j1, 8 + j1 * 18, 142));
		}
	}

	@Override
	public H getHandler() {
		return handler;
	}

	protected abstract void addSlots(InventoryPlayer inventory);

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	public boolean sameGui(ContainerBase otherContainer) {
		return this.handler == otherContainer.handler;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		return transferStackInSlot(inventorySlots, player, slotIndex);
	}

	public static ItemStack transferStackInSlot(List inventorySlots, EntityPlayer player, int slotIndex) {
		Slot slot = (Slot) inventorySlots.get(slotIndex);
		if (slot == null || !slot.getHasStack()) {
			return null;
		}

		boolean fromCraftingSlot = slot instanceof SlotCrafting;

		int numSlots = inventorySlots.size();
		ItemStack stackInSlot = slot.getStack();
		ItemStack originalStack = stackInSlot.copy();

		if (!shiftItemStack(inventorySlots, stackInSlot, slotIndex, numSlots, fromCraftingSlot)) {
			return null;
		}

		slot.onSlotChange(stackInSlot, originalStack);
		if (stackInSlot.stackSize <= 0) {
			slot.putStack(null);
		} else {
			slot.onSlotChanged();
		}

		if (stackInSlot.stackSize == originalStack.stackSize) {
			return null;
		}

		slot.onPickupFromSlot(player, stackInSlot);
		return originalStack;
	}

	private static boolean shiftItemStack(List inventorySlots, ItemStack stackInSlot, int slotIndex, int numSlots, boolean fromCraftingSlot) {
		if (isInPlayerInventory(slotIndex)) {
			if (shiftToMachineInventory(inventorySlots, stackInSlot, numSlots)) {
				return true;
			}

			if (isInPlayerHotbar(slotIndex)) {
				return shiftToPlayerInventoryNoHotbar(inventorySlots, stackInSlot);
			} else {
				return shiftToHotbar(inventorySlots, stackInSlot);
			}
		} else {
			if (fromCraftingSlot) {
				if (shiftToMachineInventory(inventorySlots, stackInSlot, numSlots)) {
					return true;
				}
			}
			return shiftToPlayerInventory(inventorySlots, stackInSlot);
		}
	}

	private static boolean shiftToPlayerInventory(List inventorySlots, ItemStack stackInSlot) {
		int playerHotbarStart = playerInventorySize - playerHotbarSize;

		// try to merge with existing stacks, hotbar first
		boolean shifted = shiftItemStackToRangeMerge(inventorySlots, stackInSlot, playerHotbarStart, playerHotbarSize);
		shifted |= shiftItemStackToRangeMerge(inventorySlots, stackInSlot, 0, playerHotbarStart);

		// shift to open slots, hotbar first
		shifted |= shiftItemStackToRangeOpenSlots(inventorySlots, stackInSlot, playerHotbarStart, playerHotbarSize);
		shifted |= shiftItemStackToRangeOpenSlots(inventorySlots, stackInSlot, 0, playerHotbarStart);
		return shifted;
	}

	private static boolean shiftToPlayerInventoryNoHotbar(List inventorySlots, ItemStack stackInSlot) {
		int playerHotbarStart = playerInventorySize - playerHotbarSize;
		return shiftItemStackToRange(inventorySlots, stackInSlot, 0, playerHotbarStart);
	}

	private static boolean shiftToHotbar(List inventorySlots, ItemStack stackInSlot) {
		int playerHotbarStart = playerInventorySize - playerHotbarSize;
		return shiftItemStackToRange(inventorySlots, stackInSlot, playerHotbarStart, playerHotbarSize);
	}

	private static boolean shiftToMachineInventory(List inventorySlots, ItemStack stackToShift, int numSlots) {
		boolean success = false;
		if (stackToShift.isStackable()) {
			success = shiftToMachineInventory(inventorySlots, stackToShift, numSlots, true);
		}
		if (stackToShift.stackSize > 0) {
			success |= shiftToMachineInventory(inventorySlots, stackToShift, numSlots, false);
		}
		return success;
	}

	// if mergeOnly = true, don't shift into empty slots.
	private static boolean shiftToMachineInventory(List inventorySlots, ItemStack stackToShift, int numSlots, boolean mergeOnly) {
		for (int machineIndex = playerInventorySize; machineIndex < numSlots; machineIndex++) {
			Slot slot = (Slot) inventorySlots.get(machineIndex);
			if (mergeOnly && slot.getStack() == null) {
				continue;
			}
			if (!slot.isItemValid(stackToShift)) {
				continue;
			}
			if (shiftItemStackToRange(inventorySlots, stackToShift, machineIndex, 1)) {
				return true;
			}
		}
		return false;
	}

	private static boolean shiftItemStackToRange(List inventorySlots, ItemStack stackToShift, int start, int count) {
		boolean changed = shiftItemStackToRangeMerge(inventorySlots, stackToShift, start, count);
		changed |= shiftItemStackToRangeOpenSlots(inventorySlots, stackToShift, start, count);
		return changed;
	}

	private static boolean shiftItemStackToRangeMerge(List inventorySlots, ItemStack stackToShift, int start, int count) {
		if (stackToShift == null || !stackToShift.isStackable() || stackToShift.stackSize <= 0) {
			return false;
		}

		boolean changed = false;
		for (int slotIndex = start; stackToShift.stackSize > 0 && slotIndex < start + count; slotIndex++) {
			Slot slot = (Slot) inventorySlots.get(slotIndex);
			ItemStack stackInSlot = slot.getStack();
			if (stackInSlot != null && ItemUtil.isIdenticalItem(stackInSlot, stackToShift)) {
				int resultingStackSize = stackInSlot.stackSize + stackToShift.stackSize;
				int max = Math.min(stackToShift.getMaxStackSize(), slot.getSlotStackLimit());
				if (resultingStackSize <= max) {
					stackToShift.stackSize = 0;
					stackInSlot.stackSize = resultingStackSize;
					slot.onSlotChanged();
					changed = true;
				} else if (stackInSlot.stackSize < max) {
					stackToShift.stackSize -= max - stackInSlot.stackSize;
					stackInSlot.stackSize = max;
					slot.onSlotChanged();
					changed = true;
				}
			}
		}
		return changed;
	}


	private static boolean shiftItemStackToRangeOpenSlots(List inventorySlots, ItemStack stackToShift, int start, int count) {
		if (stackToShift == null || stackToShift.stackSize <= 0) {
			return false;
		}

		boolean changed = false;
		for (int slotIndex = start; stackToShift.stackSize > 0 && slotIndex < start + count; slotIndex++) {
			Slot slot = (Slot) inventorySlots.get(slotIndex);
			ItemStack stackInSlot = slot.getStack();
			if (stackInSlot == null) {
				int max = Math.min(stackToShift.getMaxStackSize(), slot.getSlotStackLimit());
				stackInSlot = stackToShift.copy();
				stackInSlot.stackSize = Math.min(stackToShift.stackSize, max);
				stackToShift.stackSize -= stackInSlot.stackSize;
				slot.putStack(stackInSlot);
				slot.onSlotChanged();
				changed = true;
			}
		}
		return changed;
	}

	private static final int playerInventorySize = 9 * 4;
	private static final int playerHotbarSize = 9;

	private static boolean isInPlayerInventory(int slotIndex) {
		return slotIndex < playerInventorySize;
	}

	public static boolean isSlotInRange(int slotIndex, int start, int count) {
		return slotIndex >= start && slotIndex < start + count;
	}

	private static boolean isInPlayerHotbar(int slotIndex) {
		return isSlotInRange(slotIndex, playerInventorySize - playerHotbarSize, playerInventorySize);
	}
}
