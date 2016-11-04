package modularmachines.common.inventory;

import java.util.ArrayList;
import java.util.List;

import modularmachines.api.modular.IModularAssembler;
import modularmachines.api.modular.assembler.IAssemblerContainer;
import modularmachines.api.modular.assembler.SlotAssemblerStorage;
import modularmachines.api.modular.handlers.IModularHandler;
import modularmachines.api.modules.position.IStoragePosition;
import modularmachines.api.modules.storage.IStoragePage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAssembler extends BaseContainer<IModularHandler> implements IAssemblerContainer {

	private IModularAssembler assembler;
	private final IStoragePage page;
	private boolean afterPage = false;
	private boolean transferStack = false;
	private boolean hasStorageChange = false;

	public ContainerAssembler(IModularHandler tile, InventoryPlayer inventory) {
		super(tile, inventory);
		assembler = handler.getAssembler();
		IStoragePosition position = assembler.getSelectedPosition();
		assembler.updatePages(null);
		this.page = assembler.getStoragePage(position);
		// Add slots to container
		if (page == null) {
			addSlotToContainer(new SlotAssemblerStorage(assembler, 44, 35, null, position, this));
		} else {
			List<Slot> slots = new ArrayList<>();
			page.createSlots(this, slots);
			for(Slot slot : slots) {
				addSlotToContainer(slot);
			}
		}
		if (page != null) {
			page.setContainer(this);
			page.onSlotChanged(this);
		}
	}

	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
		assembler.beforeSlotClick(slotId, dragType, clickTypeIn, player);
		ItemStack stack = super.slotClick(slotId, dragType, clickTypeIn, player);
		assembler.afterSlotClick(slotId, dragType, clickTypeIn, player);
		return stack;
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);
		if (page != null) {
			page.setContainer(null);
		}
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if (page != null) {
			page.detectAndSendChanges();
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		return super.transferStackInSlot(player, slotIndex);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
	}

	@Override
	public boolean transferStack() {
		return transferStack;
	}

	@Override
	public boolean hasStorageChange() {
		return hasStorageChange;
	}

	@Override
	public void setHasStorageChange(boolean hasChange) {
		this.hasStorageChange = hasChange;
	}
}
