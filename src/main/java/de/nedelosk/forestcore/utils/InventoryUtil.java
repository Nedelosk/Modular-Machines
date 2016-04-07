package de.nedelosk.forestcore.utils;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityChest;

public class InventoryUtil {

	public static void readFromNBT(IInventory inventory, NBTTagCompound nbtTag) {
		if (!nbtTag.hasKey(inventory.getInventoryName())) {
			return;
		}
		NBTTagList nbttaglist = nbtTag.getTagList(inventory.getInventoryName(), 10);
		for(int j = 0; j < nbttaglist.tagCount(); ++j) {
			NBTTagCompound nbtTag2 = nbttaglist.getCompoundTagAt(j);
			int index = nbtTag2.getByte("Slot");
			inventory.setInventorySlotContents(index, ItemStack.loadItemStackFromNBT(nbtTag2));
		}
	}

	public static void writeToNBT(IInventory inventory, NBTTagCompound nbtTag) {
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			if (inventory.getStackInSlot(i) != null) {
				NBTTagCompound nbtTag2 = new NBTTagCompound();
				nbtTag2.setByte("Slot", (byte) i);
				inventory.getStackInSlot(i).writeToNBT(nbtTag2);
				nbttaglist.appendTag(nbtTag2);
			}
		}
		nbtTag.setTag(inventory.getInventoryName(), nbttaglist);
	}

	public static IInventory getInventory(IInventory inv) {
		if (inv instanceof TileEntityChest) {
			TileEntityChest chest = (TileEntityChest) inv;
			TileEntityChest neighbour = null;
			if (chest.adjacentChestXNeg != null) {
				neighbour = chest.adjacentChestXNeg;
			} else if (chest.adjacentChestXPos != null) {
				neighbour = chest.adjacentChestXPos;
			} else if (chest.adjacentChestZNeg != null) {
				neighbour = chest.adjacentChestZNeg;
			} else if (chest.adjacentChestZPos != null) {
				neighbour = chest.adjacentChestZPos;
			}
			if (neighbour != null) {
				return new InventoryLargeChest("", inv, neighbour);
			}
			return inv;
		}
		return inv;
	}
}
