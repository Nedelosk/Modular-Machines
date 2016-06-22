package de.nedelosk.forestmods.library.inventory;

import de.nedelosk.modularmachines.api.inventory.IContainerBase;
import de.nedelosk.modularmachines.api.inventory.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class ContainerBase<H extends IGuiHandler> extends Container implements IContainerBase<H> {

	protected H handler;

	public ContainerBase(H tile, InventoryPlayer inventory) {
		this.handler = tile;
		addInventory(inventory);
		addSlots(inventory);
	}

	protected void addInventory(InventoryPlayer inventory) {
		for(int i1 = 0; i1 < 3; i1++) {
			for(int l1 = 0; l1 < 9; l1++) {
				addSlotToContainer(new Slot(inventory, l1 + i1 * 9 + 9, 8 + l1 * 18, 84 + i1 * 18));
			}
		}
		for(int j1 = 0; j1 < 9; j1++) {
			addSlotToContainer(new Slot(inventory, j1, 8 + j1 * 18, 142));
		}
	}

	@Override
	public void addSlot(Slot slot) {
		addSlotToContainer(slot);
	}

	@Override
	public H getHandler() {
		return handler;
	}

	protected abstract void addSlots(InventoryPlayer inventory);

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	public boolean sameGui(ContainerBase otherContainer) {
		return this.handler == otherContainer.handler;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_transferStackInSlot_1_, int p_transferStackInSlot_2_) {
		return null;
	}
}