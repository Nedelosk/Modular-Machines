package de.nedelosk.modularmachines.common.inventory.slots;

import de.nedelosk.modularmachines.api.modular.IAssemblerLogic;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotAssembler extends SlotItemHandler {

	protected SlotAssemblerStorage storageSlot;
	private Container parent;
	public boolean isActive;
	public boolean hasChange;
	public IAssemblerLogic logic;

	public SlotAssembler(IItemHandler inventory, int index, int xPosition, int yPosition, IAssemblerLogic logic, Container parent, SlotAssemblerStorage storageSlot) {
		super(inventory, index, xPosition, yPosition);

		this.logic = logic;
		this.storageSlot = storageSlot;
		this.parent = parent;
		this.isActive = true;
		this.hasChange = false;
	}

	@Override
	public void onSlotChanged() {
		parent.onCraftMatrixChanged(inventory);
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
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
		return logic.isItemValid(stack, this, storageSlot);
	}
}
