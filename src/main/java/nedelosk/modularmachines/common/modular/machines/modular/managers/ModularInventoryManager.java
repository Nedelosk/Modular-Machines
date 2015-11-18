package nedelosk.modularmachines.common.modular.machines.modular.managers;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;

import com.google.common.collect.Maps;

import nedelosk.modularmachines.api.modular.machines.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.machines.manager.IModularInventoryManager;
import nedelosk.modularmachines.api.modular.module.tool.producer.inventory.IProducerInventory;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;

public class ModularInventoryManager implements IModularInventoryManager {

	public HashMap<String, ItemStack[]> slots = Maps.newHashMap();

	public ModularInventoryManager(NBTTagCompound nbt) {
		readFromNBT(nbt);
	}

	public ModularInventoryManager(IModularInventory modular) {
		for (Vector<ModuleStack> stacks : modular.getModules().values()) {
			for (ModuleStack module : stacks) {
				if (module.getProducer() instanceof IProducerInventory)
					slots.put(module.getModule().getName(module, false),
							new ItemStack[((IProducerInventory) module.getProducer()).getSizeInventory(module)]);
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagList nbtList = nbt.getTagList("slots", 10);
		for (int i = 0; i < nbtList.tagCount(); i++) {
			NBTTagCompound compound = nbtList.getCompoundTagAt(i);
			String key = compound.getString("Key");
			NBTTagList nbtTagList = compound.getTagList("slots", 10);
			ItemStack[] slots = new ItemStack[compound.getInteger("Size")];

			for (int r = 0; r < nbtTagList.tagCount(); r++) {
				NBTTagCompound item = nbtTagList.getCompoundTagAt(r);
				byte itemLocation = item.getByte("item");
				if (itemLocation >= 0 && itemLocation < slots.length) {
					slots[itemLocation] = ItemStack.loadItemStackFromNBT(item);
				}
			}
			this.slots.put(key, slots);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagList nbtList = new NBTTagList();
		for (Entry<String, ItemStack[]> entry : slots.entrySet()) {
			NBTTagCompound compound = new NBTTagCompound();
			compound.setString("Key", entry.getKey());
			compound.setInteger("Size", entry.getValue().length);
			NBTTagList nbtTagList = new NBTTagList();
			for (int i = 0; i < entry.getValue().length; i++) {
				if (entry.getValue()[i] != null) {
					NBTTagCompound item = new NBTTagCompound();
					item.setByte("item", (byte) i);
					entry.getValue()[i].writeToNBT(item);
					nbtTagList.appendTag(item);
				}
			}
			compound.setTag("slots", nbtTagList);
			nbtList.appendTag(compound);
		}
		nbt.setTag("slots", nbtList);
	}

	@Override
	public int getSizeInventory(String page) {
		return slots.get(page).length;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(String page, int i) {
		if (this.slots.get(page)[i] != null) {
			ItemStack itemstack = this.slots.get(page)[i];
			this.slots.get(page)[i] = null;
			return itemstack;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlot(String page, int i) {
		return this.slots.get(page)[i];
	}

	@Override
	public ItemStack decrStackSize(String page, int i, int amount) {
		if (this.slots.get(page)[i] != null) {
			ItemStack itemstack;

			if (this.slots.get(page)[i].stackSize <= amount) {
				itemstack = this.slots.get(page)[i];
				this.slots.get(page)[i] = null;
				return itemstack;
			} else {
				itemstack = this.slots.get(page)[i].splitStack(amount);

				if (this.slots.get(page)[i].stackSize == 0) {
					this.slots.get(page)[i] = null;
				}
				return itemstack;
			}
		}

		return null;
	}

	@Override
	public void setInventorySlotContents(String page, int slot, ItemStack itemstack) {
		this.slots.get(page)[slot] = itemstack;

		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public void markDirty() {
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {

	}

	@Override
	public boolean isItemValidForSlot(String page, int slot, ItemStack stack) {
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(ForgeDirection side) {
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, ForgeDirection side) {
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, ForgeDirection side) {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean addToOutput(ItemStack output, int slotMin, int slotMax, String page) {
		if (output == null)
			return true;
		for (int i = slotMin; i < slotMax; i++) {
			ItemStack itemStack = getStackInSlot(page, i);
			if (itemStack == null) {
				setInventorySlotContents(page, i, output);
				return true;
			} else {
				if (itemStack.getItem() == output.getItem() && itemStack.getItemDamage() == output.getItemDamage()) {
					if (itemStack.stackSize < itemStack.getMaxStackSize()) {
						int avaiableSpaceOnStack = itemStack.getMaxStackSize() - itemStack.stackSize;
						if (avaiableSpaceOnStack >= output.stackSize) {
							itemStack.stackSize = itemStack.stackSize + output.stackSize;
							setInventorySlotContents(page, i, itemStack);
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
