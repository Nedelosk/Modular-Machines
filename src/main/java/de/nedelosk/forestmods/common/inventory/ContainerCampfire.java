package de.nedelosk.forestmods.common.inventory;

import de.nedelosk.forestcore.inventory.ContainerBase;
import de.nedelosk.forestcore.inventory.slots.SlotOutput;
import de.nedelosk.forestmods.common.blocks.tile.TileCampfire;
import de.nedelosk.forestmods.common.crafting.CampfireRecipeManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerCampfire extends ContainerBase<TileCampfire> {

	public ContainerCampfire(TileCampfire tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		addSlotToContainer(new Slot(inventoryBase, 0, 25, 35) {

			@Override
			public boolean isItemValid(ItemStack stack) {
				if (CampfireRecipeManager.isInput(stack)) {
					return true;
				}
				return false;
			}
		});
		addSlotToContainer(new Slot(inventoryBase, 1, 43, 35) {

			@Override
			public boolean isItemValid(ItemStack stack) {
				if (CampfireRecipeManager.isInput(stack)) {
					return true;
				}
				return false;
			}
		});
		addSlotToContainer(new Slot(inventoryBase, 2, 65, 53) {

			@Override
			public boolean isItemValid(ItemStack stack) {
				if (TileEntityFurnace.getItemBurnTime(stack) > 0) {
					return true;
				}
				return false;
			}
		});
		addSlotToContainer(new SlotOutput(inventoryBase, 3, 116, 35));
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotID);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (slotID == 39) {
				if (!this.mergeItemStack(itemstack1, 0, 36, true)) {
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			} else if (slotID != 36 && slotID != 37 && slotID != 38) {
				if (CampfireRecipeManager.isInput(itemstack1)) {
					if (!this.mergeItemStack(itemstack1, 36, 38, false)) {
						return null;
					}
				} else if (TileEntityFurnace.getItemBurnTime(itemstack1) > 0) {
					if (!this.mergeItemStack(itemstack1, 38, 39, false)) {
						return null;
					}
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
