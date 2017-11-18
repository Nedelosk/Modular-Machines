package modularmachines.common.containers;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import modularmachines.api.ILocatable;
import modularmachines.api.ILocatableSource;
import modularmachines.common.utils.ContainerUtil;

public abstract class BaseContainer<S extends ILocatableSource> extends Container {

	protected S source;
	protected EntityPlayer player;

	public BaseContainer(S source, InventoryPlayer inventory) {
		this.source = source;
		this.player = inventory.player;
		addInventory(inventory);
		addSlots(inventory);
	}

	protected void addInventory(InventoryPlayer inventory) {
		for (int i1 = 0; i1 < 3; i1++) {
			for (int l1 = 0; l1 < 9; l1++) {
				addSlotToContainer(new Slot(inventory, l1 + i1 * 9 + 9, 8 + l1 * 18, 84 + i1 * 18));
			}
		}
		for (int j1 = 0; j1 < 9; j1++) {
			addSlotToContainer(new Slot(inventory, j1, 8 + j1 * 18, 142));
		}
	}

	public List<IContainerListener> getListeners() {
		return listeners;
	}
	
	public S getSource() {
		return source;
	}

	protected abstract void addSlots(InventoryPlayer inventory);

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		ILocatable locatable = source.getLocatable();
		if (locatable != null) {
			return locatable.isUsableByPlayer(player);
		}
		return true;
	}

	public boolean sameGui(BaseContainer otherContainer) {
		return this.source == otherContainer.source;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		return ContainerUtil.transferStackInSlot(inventorySlots, player, slotIndex);
	}

	public EntityPlayer getPlayer() {
		return player;
	}

	public List<Slot> getSlots() {
		return inventorySlots;
	}
}
