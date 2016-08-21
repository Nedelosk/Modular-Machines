package de.nedelosk.modularmachines.common.inventory.slots;

import de.nedelosk.modularmachines.api.modular.IAssemblerLogic;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotAssemblerStorage extends SlotItemHandler {

	protected IAssemblerLogic logic;
	private Container parent;

	public SlotAssemblerStorage(IItemHandler inventory, int index, int xPosition, int yPosition, Container parent, IAssemblerLogic logic) {
		super(inventory, index, xPosition, yPosition);

		this.parent = parent;
		this.logic = logic;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

	@Override
	public void onSlotChanged() {
		super.onSlotChanged();
		parent.onCraftMatrixChanged(inventory);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return logic.isItemValid(stack, this, null);
	}
}
