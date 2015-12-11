package nedelosk.forestday.common.inventory;

import nedelosk.forestcore.api.INBTTagable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class ExternalInventory implements IInventory, INBTTagable {

	public ItemStack[] slots;

	public ExternalInventory(int slots) {
		this.slots = new ItemStack[slots];
	}

	@Override
	public int getSizeInventory() {
		return this.slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return this.slots[i];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (this.slots[slot] != null) {
			ItemStack itemstack;

			if (this.slots[slot].stackSize <= amount) {
				itemstack = this.slots[slot];
				this.slots[slot] = null;
				return itemstack;
			} else {
				itemstack = this.slots[slot].splitStack(amount);

				if (this.slots[slot].stackSize == 0) {
					this.slots[slot] = null;
				}
			}
		}

		return null;

	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (this.slots[i] != null) {
			ItemStack itemstack = this.slots[i];
			this.slots[i] = null;
			return itemstack;
		}
		;
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.slots[i] = itemstack;

		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}

	}

	@Override
	public String getInventoryName() {
		return "";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}

	public abstract void onGuiSaved(EntityPlayer player);

	public abstract void save();

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {

		NBTTagList nbtTagList = new NBTTagList();
		for (int i = 0; i < this.getSizeInventory(); i++) {
			if (this.slots[i] != null) {
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("item", (byte) i);
				this.slots[i].writeToNBT(item);
				nbtTagList.appendTag(item);
			}
		}
		nbt.setTag("slots", nbtTagList);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {

		NBTTagList nbtTagList = nbt.getTagList("slots", 10);
		this.slots = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbtTagList.tagCount(); i++) {
			NBTTagCompound item = nbtTagList.getCompoundTagAt(i);
			byte itemLocation = item.getByte("item");
			if (itemLocation >= 0 && itemLocation < this.getSizeInventory()) {
				this.slots[itemLocation] = ItemStack.loadItemStackFromNBT(item);
			}
		}
	}

	@Override
	public void markDirty() {

	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return true;
	}

}
