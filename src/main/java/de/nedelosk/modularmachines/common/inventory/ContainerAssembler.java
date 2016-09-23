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
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAssembler extends BaseContainer<IModularHandler> implements IAssemblerContainer {

	private final IStoragePage page;
	private boolean transferStack = false;
	private boolean hasStorageChange= false;
	
	public ContainerAssembler(IModularHandler tile, InventoryPlayer inventory) {
		super(tile, inventory);
		IModularAssembler assembler = handler.getAssembler();
		page = assembler.getStoragePage(assembler.getSelectedPosition());
		if(page != null){
			page.onSlotChanged(this);
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
		IModularAssembler assembler = handler.getAssembler();
		IStoragePosition position = assembler.getSelectedPosition();
		IStoragePage page = assembler.getStoragePage(position);
		if(page == null){
			addSlotToContainer(new SlotAssemblerStorage(assembler.getItemHandler(), assembler.getIndex(position), 44, 35, null, position, this));
		}else{
			List<Slot> slots = new ArrayList<>();
			page.createSlots(this, slots);
			for(Slot slot : slots){
				addSlotToContainer(slot);
			}
		}
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
