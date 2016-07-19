package de.nedelosk.modularmachines.common.inventory.slots;

import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotAssemblerStorage extends SlotItemHandler {

	protected IModularAssembler assembler;

	public SlotAssemblerStorage(IItemHandler inventory, int index, int xPosition, int yPosition, IModularAssembler assembler) {
		super(inventory, index, xPosition, yPosition);

		this.assembler = assembler;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return assembler.getLogic(assembler.getSelectedPosition()).isItemValid(stack, this, null);
	}
}
