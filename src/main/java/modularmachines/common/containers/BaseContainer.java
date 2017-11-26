package modularmachines.common.containers;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import modularmachines.api.IGuiProvider;
import modularmachines.api.ILocatable;
import modularmachines.common.utils.ContainerUtil;

public abstract class BaseContainer<P extends IGuiProvider> extends Container {
	
	protected P provider;
	protected EntityPlayer player;
	
	public BaseContainer(P provider, InventoryPlayer inventory) {
		this.provider = provider;
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
	
	public P getProvider() {
		return provider;
	}
	
	protected abstract void addSlots(InventoryPlayer inventory);
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		ILocatable locatable = provider.getLocatable();
		return locatable.isUsableByPlayer(player);
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
