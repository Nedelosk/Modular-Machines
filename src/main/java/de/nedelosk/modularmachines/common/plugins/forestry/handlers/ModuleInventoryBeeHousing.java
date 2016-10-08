package de.nedelosk.modularmachines.common.plugins.forestry.handlers;

import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import forestry.api.apiculture.IBeeHousingInventory;
import forestry.apiculture.InventoryBeeHousing;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

public class ModuleInventoryBeeHousing implements IBeeHousingInventory {

	private final IModuleInventory inventory;

	public ModuleInventoryBeeHousing(IModuleInventory inventory) {
		this.inventory = inventory;
	}

	@Override
	public final ItemStack getQueen() {
		return inventory.getStackInSlot(InventoryBeeHousing.SLOT_QUEEN);
	}

	@Override
	public final ItemStack getDrone() {
		return inventory.getStackInSlot(InventoryBeeHousing.SLOT_DRONE);
	}

	@Override
	public final void setQueen(ItemStack itemstack) {
		inventory.setStackInSlot(InventoryBeeHousing.SLOT_QUEEN, itemstack);
	}

	@Override
	public final void setDrone(ItemStack itemstack) {
		inventory.setStackInSlot(InventoryBeeHousing.SLOT_DRONE, itemstack);
	}

	@Override
	public final boolean addProduct(ItemStack product, boolean all) {
		return tryAddStack(inventory, product, InventoryBeeHousing.SLOT_PRODUCT_1, InventoryBeeHousing.SLOT_PRODUCT_COUNT, all, true);
	}

	private static boolean tryAddStack(IItemHandlerModifiable inventory, ItemStack stack, int startSlot, int slots, boolean all, boolean doAdd) {
		int added = addStack(inventory, stack, startSlot, slots, false);
		boolean success = all ? added == stack.stackSize : added > 0;

		if (success && doAdd) {
			addStack(inventory, stack, startSlot, slots, true);
		}

		return success;
	}

	private static int addStack(IItemHandlerModifiable inventory, ItemStack stack, int startSlot, int slots, boolean doAdd) {

		int added = 0;
		// Add to existing stacks first
		for (int i = startSlot; i < startSlot + slots; i++) {

			ItemStack inventoryStack = inventory.getStackInSlot(i);
			// Empty slot. Add
			if (inventoryStack == null || inventoryStack.getItem() == null) {
				continue;
			}

			// Already occupied by different item, skip this slot.
			if (!inventoryStack.isStackable()) {
				continue;
			}
			if (!inventoryStack.isItemEqual(stack)) {
				continue;
			}
			if (!ItemStack.areItemStackTagsEqual(inventoryStack, stack)) {
				continue;
			}

			int remain = stack.stackSize - added;
			int space = inventoryStack.getMaxStackSize() - inventoryStack.stackSize;
			// No space left, skip this slot.
			if (space <= 0) {
				continue;
			}
			// Enough space
			if (space >= remain) {
				if (doAdd) {
					inventoryStack.stackSize += remain;
				}
				return stack.stackSize;
			}

			// Not enough space
			if (doAdd) {
				inventoryStack.stackSize = inventoryStack.getMaxStackSize();
			}

			added += space;
		}

		if (added >= stack.stackSize) {
			return added;
		}

		for (int i = startSlot; i < startSlot + slots; i++) {
			if (inventory.getStackInSlot(i) != null) {
				continue;
			}

			if (doAdd) {
				inventory.setStackInSlot(i, stack.copy());
				inventory.getStackInSlot(i).stackSize = stack.stackSize - added;
			}
			return stack.stackSize;

		}

		return added;
	}

}
