package de.nedelosk.modularmachines.common.inventory;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerContainer;
import de.nedelosk.modularmachines.api.modular.assembler.SlotAssemblerStorage;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.api.modules.storage.IStoragePage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAssembler extends BaseContainer<IModularHandler> implements IAssemblerContainer {

	private final IStoragePage page;
	private boolean afterPage = false;
	private boolean transferStack = false;
	private boolean hasStorageChange= false;

	public ContainerAssembler(IModularHandler tile, InventoryPlayer inventory) {
		super(tile, inventory);
		IModularAssembler assembler = handler.getAssembler();
		IStoragePosition position = assembler.getSelectedPosition();
		this.page = assembler.getStoragePage(position);
		assembler.updatePages(null);

		//Add slots to container
		if(page == null){
			addSlotToContainer(new SlotAssemblerStorage(assembler, 44, 35, null, position, this));
		}else{
			List<Slot> slots = new ArrayList<>();
			page.createSlots(this, slots);
			for(Slot slot : slots){
				addSlotToContainer(slot);
			}
		}

		if(page != null){
			page.setContainer(this);
			page.onSlotChanged(this);
		}
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);
		if(page != null){
			page.setContainer(null);
		}
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if(page != null){
			page.detectAndSendChanges();
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		transferStack = true;
		ItemStack stack = super.transferStackInSlot(player, slotIndex);
		transferStack = false;
		if(hasStorageChange){
			handler.getAssembler().onStorageChange();
			hasStorageChange = false;
		}
		return stack;
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
