package modularmachines.api.modules.assemblers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import modularmachines.common.containers.ContainerAssembler;

public class SlotAssembler extends SlotItemHandler {

	protected final SlotAssemblerStorage storageSlot;
	protected final StoragePage page;
	protected final ContainerAssembler container;
	public boolean isActive;
	public boolean hasChange;

	public SlotAssembler(IItemHandler inventory, int index, int xPosition, int yPosition, StoragePage page, ContainerAssembler container, SlotAssemblerStorage storageSlot) {
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
		page.onSlotChanged(container, container.getSource());
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
