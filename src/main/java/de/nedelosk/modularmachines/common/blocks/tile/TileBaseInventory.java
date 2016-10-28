package de.nedelosk.modularmachines.common.blocks.tile;

import de.nedelosk.modularmachines.common.utils.InventoryUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public abstract class TileBaseInventory extends TileBaseGui implements ISidedInventory {

	private final IItemHandler itemHandler = new SidedInvWrapper(this, null);
	public ItemStack[] slots;

	public TileBaseInventory(int slots) {
		this.slots = new ItemStack[slots];
	}

	public TileBaseInventory() {
	}

	@Override
	public int getSizeInventory() {
		if (slots == null) {
			return 0;
		}
		return this.slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if (slots == null) {
			return null;
		}
		return this.slots[i];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (slots == null) {
			return null;
		}
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
	public ItemStack removeStackFromSlot(int index) {
		if (slots == null) {
			return null;
		}
		if (this.slots[index] != null) {
			ItemStack itemstack = this.slots[index];
			this.slots[index] = null;
			return itemstack;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		if (slots == null) {
			return;
		}
		this.slots[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}
	}

	public abstract String getTitle();

	@Override
	public String getName() {
		return getTitle();
	}

	@Override
	public boolean hasCustomName() {
		return getName() != null;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString(getName());
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entitiPlayer) {
		return this.worldObj.getTileEntity(pos) != this ? false : entitiPlayer.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer entitiPlayer) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, EnumFacing side) {
		return this.isItemValidForSlot(slot, itemstack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side) {
		return true;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[0];
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		InventoryUtil.writeToNBT(this, nbt);
		return super.writeToNBT(nbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		InventoryUtil.readFromNBT(this, nbt);
	}

	public boolean addToOutput(ItemStack output, int slotMin, int slotMax) {
		if (output == null) {
			return true;
		}
		for(int i = slotMin; i < slotMax; i++) {
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

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemHandler);
		}
		return super.getCapability(capability, facing);
	}
}
