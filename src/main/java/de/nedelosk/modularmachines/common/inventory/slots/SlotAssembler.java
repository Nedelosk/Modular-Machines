package de.nedelosk.modularmachines.common.inventory.slots;

import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotAssembler extends SlotItemHandler {

	protected IModularAssembler assembler;
	protected SlotAssemblerStorage storageSlot;
	public boolean isActive;

	public SlotAssembler(IItemHandler inventory, int index, int xPosition, int yPosition, IModularAssembler assembler, SlotAssemblerStorage storageSlot) {
		super(inventory, index, xPosition, yPosition);

		this.assembler = assembler;
		this.storageSlot = storageSlot;
		this.isActive = true;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		if(!isActive){
			return false;
		}
		return assembler.getLogic(assembler.getSelectedPosition()).isItemValid(stack, this, storageSlot);
	}
}
