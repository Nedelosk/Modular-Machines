package nedelosk.forestday.api.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class TileBaseInventory extends TileBaseGui implements ISidedInventory {

	public ItemStack[] slots;

	public TileBaseInventory(int slots) {
		this.slots = new ItemStack[slots];
	}

	public TileBaseInventory() {
	}

	@Override
	public int getSizeInventory() {
		if (slots == null)
			return 0;
		return this.slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if (slots == null)
			return null;
		return this.slots[i];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (slots == null)
			return null;
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
				return itemstack;
			}
		}

		return null;

	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (slots == null)
			return null;
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
		if (slots == null)
			return;
		this.slots[i] = itemstack;

		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}

	}

	public abstract String getMachineTileName();

	@Override
	public String getInventoryName() {
		return getMachineTileName();
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
	public boolean isUseableByPlayer(EntityPlayer entitiPlayer) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false
				: entitiPlayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int j) {
		return this.isItemValidForSlot(slot, itemstack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int p_102008_3_) {
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[0];
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if (slots != null && slots.length > 0) {
			nbt.setInteger("Size", slots.length);
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
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		if (nbt.hasKey("slots")) {
			NBTTagList nbtTagList = nbt.getTagList("slots", 10);
			this.slots = new ItemStack[nbt.getInteger("Size")];

			for (int i = 0; i < nbtTagList.tagCount(); i++) {
				NBTTagCompound item = nbtTagList.getCompoundTagAt(i);
				byte itemLocation = item.getByte("item");
				if (itemLocation >= 0 && itemLocation < this.getSizeInventory()) {
					this.slots[itemLocation] = ItemStack.loadItemStackFromNBT(item);
				}
			}
		}
	}

	public boolean addToOutput(ItemStack output, int slotMin, int slotMax) {
		if (output == null)
			return true;

		for (int i = slotMin; i < slotMax; i++) {
			ItemStack itemStack = getStackInSlot(i);
			if (itemStack == null) {
				setInventorySlotContents(i, output);
				return true;
			} else {
				if (itemStack.getItem() == output.getItem() && itemStack.getItemDamage() == output.getItemDamage()) {
					if (itemStack.stackSize < itemStack.getMaxStackSize()) {
						int avaiableSpaceOnStack = itemStack.getMaxStackSize() - itemStack.stackSize;
						if (avaiableSpaceOnStack >= output.stackSize) {
							itemStack.stackSize = itemStack.stackSize + output.stackSize;
							setInventorySlotContents(i, itemStack);
							return true;
						} else {
							return false;
						}
					}
				}
			}
		}
		return false;
	}

}
