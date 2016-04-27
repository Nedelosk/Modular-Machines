package de.nedelosk.forestmods.library.inventory;

import de.nedelosk.forestmods.library.utils.InventoryUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class InventorySimple implements IInventory {

	private final ItemStack[] contents;
	private final String name;
	private final int stackLimit;

	public InventorySimple(int size, String name) {
		this(size, name, 64);
	}

	public InventorySimple(int size, String name, int stackLimit) {
		this.contents = new ItemStack[size];
		this.name = name;
		this.stackLimit = stackLimit;
	}

	public InventorySimple(IInventory tocopy) {
		this(tocopy.getSizeInventory(), tocopy.getInventoryName(), tocopy.getInventoryStackLimit());
		for(int i = 0; i < tocopy.getSizeInventory(); i++) {
			if (tocopy.getStackInSlot(i) != null) {
				this.setInventorySlotContents(i, tocopy.getStackInSlot(i).copy());
			} else {
				this.setInventorySlotContents(i, null);
			}
		}
	}

	public ItemStack[] getContents() {
		return contents;
	}

	@Override
	public int getSizeInventory() {
		return contents.length;
	}

	@Override
	public ItemStack getStackInSlot(int slotId) {
		return contents[slotId];
	}

	@Override
	public ItemStack decrStackSize(int slotId, int count) {
		if (contents[slotId] == null) {
			return null;
		}
		if (contents[slotId].stackSize > count) {
			return contents[slotId].splitStack(count);
		}
		ItemStack stack = contents[slotId];
		contents[slotId] = null;
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slotId, ItemStack itemstack) {
		contents[slotId] = itemstack;
	}

	@Override
	public String getInventoryName() {
		return name;
	}

	@Override
	public int getInventoryStackLimit() {
		return stackLimit;
	}

	@Override
	public void markDirty() {
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return false;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slotIndex) {
		return this.getStackInSlot(slotIndex);
	}

	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	/* INBTagable */
	public void readFromNBT(NBTTagCompound nbtTag) {
		InventoryUtil.readFromNBT(this, nbtTag);
	}

	public void writeToNBT(NBTTagCompound nbtTag) {
		InventoryUtil.writeToNBT(this, nbtTag);
	}
}