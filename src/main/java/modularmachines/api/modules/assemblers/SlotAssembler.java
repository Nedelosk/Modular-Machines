package modularmachines.api.modules.assemblers;

import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

import net.minecraftforge.items.SlotItemHandler;

public class SlotAssembler extends SlotItemHandler {

	protected final SlotAssemblerStorage storageSlot;
	protected final IAssembler assembler;
	protected final IStoragePage page;
	protected final Container container;
	public boolean isActive;
	public boolean hasChange;

	public SlotAssembler(IAssembler assembler, Container container, int index, int xPosition, int yPosition, IStoragePage page, SlotAssemblerStorage storageSlot) {
		super(page.getItemHandler(), index, xPosition, yPosition);
		this.storageSlot = storageSlot;
		this.assembler = assembler;
		this.page = page;
		this.container = container;
		this.isActive = true;
		this.hasChange = false;
	}

	@Override
	public void onSlotChanged() {
		super.onSlotChanged();
		page.onSlotChanged(container, assembler);
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
		if (!hasChange) {
			this.hasChange = true;
		}
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		if (!isActive) {
			return false;
		}
		return page.isItemValid(stack, this, storageSlot);
	}
}
