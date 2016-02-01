package nedelosk.modularmachines.api.modules.inventory;

import nedelosk.modularmachines.api.inventory.slots.SlotModular;
import nedelosk.modularmachines.api.modular.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class ModuleInventory<M extends IModule, S extends IModuleSaver> implements IModuleInventory<M, S> {

	protected ItemStack[] slots;
	protected final String UID;

	public ModuleInventory(String UID, int slots) {
		this.UID = UID;
		this.slots = new ItemStack[slots];
	}

	/* INEVNTORY */
	@Override
	public ItemStack transferStackInSlot(ModuleStack<M, S> stack, IModularTileEntity tile, EntityPlayer player, int slotID, Container container) {
		ItemStack itemstack = null;
		Slot slot = (Slot) container.inventorySlots.get(slotID);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (slot instanceof Slot && !(slot instanceof SlotModular)) {
				if (!transferInput(stack, tile, player, slotID, container, itemstack1)) {
					return null;
				}
			} else if (!mergeItemStack(itemstack1, 0, 36, false, container)) {
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

	public abstract boolean transferInput(ModuleStack<M, S> stack, IModularTileEntity tile, EntityPlayer player, int slotID, Container container,
			ItemStack stackItem);

	protected boolean mergeItemStack(ItemStack p_75135_1_, int p_75135_2_, int p_75135_3_, boolean p_75135_4_, Container container) {
		boolean flag1 = false;
		int k = p_75135_2_;
		if (p_75135_4_) {
			k = p_75135_3_ - 1;
		}
		Slot slot;
		ItemStack itemstack1;
		if (p_75135_1_.isStackable()) {
			while (p_75135_1_.stackSize > 0 && (!p_75135_4_ && k < p_75135_3_ || p_75135_4_ && k >= p_75135_2_)) {
				slot = (Slot) container.inventorySlots.get(k);
				itemstack1 = slot.getStack();
				if (itemstack1 != null && itemstack1.getItem() == p_75135_1_.getItem()
						&& (!p_75135_1_.getHasSubtypes() || p_75135_1_.getItemDamage() == itemstack1.getItemDamage())
						&& ItemStack.areItemStackTagsEqual(p_75135_1_, itemstack1)) {
					int l = itemstack1.stackSize + p_75135_1_.stackSize;
					if (l <= p_75135_1_.getMaxStackSize()) {
						p_75135_1_.stackSize = 0;
						itemstack1.stackSize = l;
						slot.onSlotChanged();
						flag1 = true;
					} else if (itemstack1.stackSize < p_75135_1_.getMaxStackSize()) {
						p_75135_1_.stackSize -= p_75135_1_.getMaxStackSize() - itemstack1.stackSize;
						itemstack1.stackSize = p_75135_1_.getMaxStackSize();
						slot.onSlotChanged();
						flag1 = true;
					}
				}
				if (p_75135_4_) {
					--k;
				} else {
					++k;
				}
			}
		}
		if (p_75135_1_.stackSize > 0) {
			if (p_75135_4_) {
				k = p_75135_3_ - 1;
			} else {
				k = p_75135_2_;
			}
			while (!p_75135_4_ && k < p_75135_3_ || p_75135_4_ && k >= p_75135_2_) {
				slot = (Slot) container.inventorySlots.get(k);
				itemstack1 = slot.getStack();
				if (itemstack1 == null) {
					slot.putStack(p_75135_1_.copy());
					slot.onSlotChanged();
					p_75135_1_.stackSize = 0;
					flag1 = true;
					break;
				}
				if (p_75135_4_) {
					--k;
				} else {
					++k;
				}
			}
		}
		return flag1;
	}

	@Override
	public String getModuleUID() {
		return UID.split(":")[1];
	}

	@Override
	public String getCategoryUID() {
		return UID.split(":")[0];
	}

	@Override
	public String getUID() {
		return null;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModularInventory modular, ModuleStack<M, S> stack) {
		NBTTagList nbttaglist = new NBTTagList();
		for ( int i = 0; i < this.slots.length; ++i ) {
			if (this.slots[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.slots[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbt.setTag("Items", nbttaglist);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModularInventory modular, ModuleStack<M, S> stack) {
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		this.slots = new ItemStack[this.getSizeInventory(stack, modular)];
		for ( int i = 0; i < nbttaglist.tagCount(); ++i ) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");
			if (b0 >= 0 && b0 < this.slots.length) {
				this.slots[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}

	@Override
	public int getSizeInventory(ModuleStack<M, S> moduleStack, IModularInventory modular) {
		return slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int index, ModuleStack<M, S> moduleStack, IModularInventory modular) {
		return index >= 0 && index < this.slots.length ? this.slots[index] : null;
	}

	@Override
	public ItemStack decrStackSize(int index, int stacksize, ModuleStack<M, S> moduleStack, IModularInventory modular) {
		if (this.slots[index] != null) {
			ItemStack itemstack;
			if (this.slots[index].stackSize <= stacksize) {
				itemstack = this.slots[index];
				this.slots[index] = null;
				this.markDirty(moduleStack, modular);
				return itemstack;
			} else {
				itemstack = this.slots[index].splitStack(stacksize);
				if (this.slots[index].stackSize == 0) {
					this.slots[index] = null;
				}
				this.markDirty(moduleStack, modular);
				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index, ModuleStack<M, S> moduleStack, IModularInventory modular) {
		if (this.slots[index] != null) {
			ItemStack itemstack = this.slots[index];
			this.slots[index] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack itemStack, ModuleStack<M, S> moduleStack, IModularInventory modular) {
		this.slots[index] = itemStack;
		if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit(moduleStack, modular)) {
			itemStack.stackSize = this.getInventoryStackLimit(moduleStack, modular);
		}
		this.markDirty(moduleStack, modular);
	}

	@Override
	public String getInventoryName(ModuleStack<M, S> moduleStack, IModularInventory modular) {
		return null;
	}

	@Override
	public boolean hasCustomInventoryName(ModuleStack<M, S> moduleStack, IModularInventory modular) {
		return false;
	}

	@Override
	public void markDirty(ModuleStack<M, S> moduleStack, IModularInventory modular) {
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player, ModuleStack<M, S> moduleStack, IModularInventory modular) {
		return true;
	}

	@Override
	public void openInventory(ModuleStack<M, S> moduleStack, IModularInventory modular) {
	}

	@Override
	public void closeInventory(ModuleStack<M, S> moduleStack, IModularInventory modular) {
	}

	@Override
	public int getInventoryStackLimit(ModuleStack<M, S> moduleStack, IModularInventory modular) {
		return 64;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack itemStack, ModuleStack<M, S> moduleStack, IModularInventory modular) {
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side, ModuleStack<M, S> moduleStack, IModularInventory modular) {
		return null;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStack, int side, ModuleStack<M, S> moduleStack, IModularInventory modular) {
		return true;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack itemStack, int side, ModuleStack<M, S> moduleStack, IModularInventory modular) {
		return false;
	}
}
