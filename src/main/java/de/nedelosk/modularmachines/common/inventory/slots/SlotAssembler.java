package de.nedelosk.modularmachines.common.inventory.slots;

import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.common.inventory.ContainerAssembler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotAssembler extends SlotItemHandler {

	protected IModularAssembler assembler;
	protected SlotAssemblerStorage storageSlot;
	private ContainerAssembler parent;
	public boolean isActive;
	public boolean hasChange;

	public SlotAssembler(IItemHandler inventory, int index, int xPosition, int yPosition, IModularAssembler assembler, ContainerAssembler parent, SlotAssemblerStorage storageSlot) {
		super(inventory, index, xPosition, yPosition);

		this.assembler = assembler;
		this.storageSlot = storageSlot;
		this.parent = parent;
		this.isActive = true;
		this.hasChange = false;
	}

	@Override
	public void onSlotChanged() {
		parent.onCraftMatrixChanged(inventory);
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
		if(!hasChange){
			this.hasChange = true;
		}
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		if(!isActive){
			return false;
		}
		return assembler.getLogic(assembler.getSelectedPosition()).isItemValid(stack, this, storageSlot);
	}
}
