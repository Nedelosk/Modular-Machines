package de.nedelosk.modularmachines.common.inventory.slots;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotModuleCrafter extends Slot {

	private Container container;

	public SlotModuleCrafter(IInventory inventoryIn, int index, int xPosition, int yPosition, Container container) {
		super(inventoryIn, index, xPosition, yPosition);
		this.container = container;
	}

	@Override
	public void onSlotChanged() {
		super.onSlotChanged();
		container.onCraftMatrixChanged(inventory);
	}
}
