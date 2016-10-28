package de.nedelosk.modularmachines.api.recipes;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

public class ModuleCraftingWrapper extends InventoryCrafting {

	private IInventory defaultInventory;

	public ModuleCraftingWrapper(Container eventHandlerIn, IInventory defaultInventory) {
		super(eventHandlerIn, 3, 3);
		this.defaultInventory = defaultInventory;
	}

	public ItemStack getHolder() {
		return defaultInventory.getStackInSlot(9);
	}

	@Override
	public ItemStack getStackInRowAndColumn(int row, int column) {
		return row >= 0 && row < 3 && column >= 0 && column <= 3 ? defaultInventory.getStackInSlot(row + column * 3) : null;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return index >= this.getSizeInventory() ? null : defaultInventory.getStackInSlot(index);
	}
}
