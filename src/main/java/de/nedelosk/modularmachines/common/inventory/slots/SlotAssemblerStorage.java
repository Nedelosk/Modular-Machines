package de.nedelosk.modularmachines.common.inventory.slots;

import de.nedelosk.modularmachines.api.modular.IAssemblerLogic;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotAssemblerStorage extends SlotItemHandler {

	protected IAssemblerLogic logic;

	public SlotAssemblerStorage(IItemHandler inventory, int index, int xPosition, int yPosition, IAssemblerLogic logic) {
		super(inventory, index, xPosition, yPosition);

		this.logic = logic;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return logic.isItemValid(stack, this, null);
	}
}
