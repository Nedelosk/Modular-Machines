package de.nedelosk.modularmachines.api.modules.storage;

import de.nedelosk.modularmachines.api.gui.IContainerBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotAssembler extends SlotItemHandler {

	protected final SlotAssemblerStorage storageSlot;
	protected final IStoragePage page;
	protected final IContainerBase container;
	public boolean isActive;
	public boolean hasChange;

	public SlotAssembler(IItemHandler inventory, int index, int xPosition, int yPosition, IStoragePage page, IContainerBase container, SlotAssemblerStorage storageSlot) {
		super(inventory, index, xPosition, yPosition);

		this.storageSlot = storageSlot;
		this.page = page;
		this.container = container;
		this.isActive = true;
		this.hasChange = false;
	}

	@Override
	public void onSlotChanged() {
		super.onSlotChanged();
		page.onSlotChanged(container);
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
		return page.isItemValid(stack, this, storageSlot);
	}
}
