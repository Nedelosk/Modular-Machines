package de.nedelosk.forestmods.common.inventory;

import java.util.List;

import com.google.common.collect.Lists;

import de.nedelosk.forestmods.library.inventory.ContainerBase;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modular.IModularTileEntity;
import de.nedelosk.forestmods.library.modules.ModuleManager;
import de.nedelosk.forestmods.library.modules.handlers.IModulePage;
import de.nedelosk.forestmods.library.modules.handlers.inventory.slots.SlotModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerModular extends ContainerBase<IModularTileEntity> {

	public InventoryPlayer inventory;
	public IModulePage currentPage;

	public ContainerModular(IModularTileEntity tileModularMachine, InventoryPlayer inventoryPlayer, IModulePage currentPage) {
		super(tileModularMachine, inventoryPlayer);
		this.inventory = inventoryPlayer;
		this.currentPage = currentPage;
		addInventory(inventoryPlayer);
		addSlots(inventoryPlayer);
	}

	@Override
	protected void addSlots(InventoryPlayer inventoryPlayer) {
		if (currentPage != null) {
			List<SlotModule> slots = Lists.newArrayList();
			if (!currentPage.getModule().isHandlerDisabled(ModuleManager.inventoryType)) {
				currentPage.createSlots(this, slots);
				for(SlotModule slot : slots) {
					addSlotToContainer(slot);
				}
			}
		}
	}

	@Override
	protected void addInventory(InventoryPlayer inventoryPlayer) {
		if (currentPage != null) {
			int invPosition = 84;
			if (!currentPage.getModule().isHandlerDisabled(ModuleManager.inventoryType)) {
				invPosition = currentPage.getPlayerInvPosition() + 1;
			}
			for(int i1 = 0; i1 < 3; i1++) {
				for(int l1 = 0; l1 < 9; l1++) {
					addSlotToContainer(new Slot(inventoryPlayer, l1 + i1 * 9 + 9, 8 + l1 * 18, invPosition + i1 * 18));
				}
			}
			for(int j1 = 0; j1 < 9; j1++) {
				addSlotToContainer(new Slot(inventoryPlayer, j1, 8 + j1 * 18, invPosition + 58));
			}
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotID);
		IModular modular = inventoryBase.getModular();
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (slot instanceof SlotModule && !currentPage.getModule().getInventory().isInput(slot.getSlotIndex())) {
				if (!this.mergeItemStack(itemstack1, 0, 36, true)) {
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			} else if (slot instanceof Slot) {
				if (currentPage.getModule() != null) {
					return currentPage.getModule().getInventory().transferStackInSlot(inventoryBase, player, slotID, this);
				} else if (slotID >= 0 && slotID < 27) {
					if (!this.mergeItemStack(itemstack1, 27, 36, false)) {
						return null;
					}
				} else if (slotID >= 27 && slotID < 36 && !this.mergeItemStack(itemstack1, 0, 27, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, 36, false)) {
				return null;
			}
			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}
			slot.onPickupFromSlot(player, itemstack1);
		}
		return itemstack;
	}
}
