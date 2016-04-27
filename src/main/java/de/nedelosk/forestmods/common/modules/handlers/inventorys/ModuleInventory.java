package de.nedelosk.forestmods.common.modules.handlers.inventorys;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.forestmods.common.modules.handlers.FilterWrapper;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modular.IModularTileEntity;
import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.modules.ModuleManager;
import de.nedelosk.forestmods.library.modules.handlers.IContentFilter;
import de.nedelosk.forestmods.library.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.forestmods.library.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.forestmods.library.recipes.RecipeItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;

public class ModuleInventory<M extends IModule> implements IModuleInventory<M> {

	protected final ItemStack[] slots;
	protected final boolean[] isInput;
	protected final IModular modular;
	protected final M module;
	protected final FilterWrapper insertFilter;
	protected final FilterWrapper extractFilter;
	protected final String inventoryName;

	public ModuleInventory(int size, boolean[] inputs, IModular modular, M module, FilterWrapper insertFilter, FilterWrapper extractFilter,
			String inventoryName) {
		this.slots = new ItemStack[size];
		this.isInput = inputs;
		this.modular = modular;
		this.module = module;
		this.insertFilter = insertFilter;
		this.extractFilter = extractFilter;
		this.inventoryName = inventoryName;
	}

	@Override
	public IModular getModular() {
		return modular;
	}

	/* INEVNTORY */
	@Override
	public ItemStack transferStackInSlot(IModularTileEntity tile, EntityPlayer player, int index, Container container) {
		ItemStack itemstack = null;
		Slot slot = (Slot) container.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (slot instanceof Slot && !(slot instanceof SlotModule)) {
				if (!module.transferInput(tile, player, index, container, itemstack1)) {
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

	@Override
	public boolean mergeItemStack(ItemStack stack, int minSlot, int maxSlot, boolean maxToMin, Container container) {
		boolean isMerged = false;
		int currentSlot = minSlot;
		if (maxToMin) {
			currentSlot = maxSlot - 1;
		}
		Slot slot;
		ItemStack slotStack;
		if (stack.isStackable()) {
			while (stack.stackSize > 0 && (!maxToMin && currentSlot < maxSlot || maxToMin && currentSlot >= minSlot)) {
				slot = (Slot) container.inventorySlots.get(currentSlot);
				slotStack = slot.getStack();
				if (slotStack != null && slotStack.getItem() == stack.getItem()
						&& (!stack.getHasSubtypes() || stack.getItemDamage() == slotStack.getItemDamage())
						&& ItemStack.areItemStackTagsEqual(stack, slotStack)) {
					int l = slotStack.stackSize + stack.stackSize;
					if (l <= stack.getMaxStackSize()) {
						stack.stackSize = 0;
						slotStack.stackSize = l;
						slot.onSlotChanged();
						isMerged = true;
					} else if (slotStack.stackSize < stack.getMaxStackSize()) {
						stack.stackSize -= stack.getMaxStackSize() - slotStack.stackSize;
						slotStack.stackSize = stack.getMaxStackSize();
						slot.onSlotChanged();
						isMerged = true;
					}
				}
				if (maxToMin) {
					--currentSlot;
				} else {
					++currentSlot;
				}
			}
		}
		if (stack.stackSize > 0) {
			if (maxToMin) {
				currentSlot = maxSlot - 1;
			} else {
				currentSlot = minSlot;
			}
			while (!maxToMin && currentSlot < maxSlot || maxToMin && currentSlot >= minSlot) {
				slot = (Slot) container.inventorySlots.get(currentSlot);
				slotStack = slot.getStack();
				if (slotStack == null) {
					slot.putStack(stack.copy());
					slot.onSlotChanged();
					stack.stackSize = 0;
					isMerged = true;
					break;
				}
				if (maxToMin) {
					--currentSlot;
				} else {
					++currentSlot;
				}
			}
		}
		return isMerged;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		if (slots.length > 0) {
			NBTTagList nbttaglist = new NBTTagList();
			for(int i = 0; i < this.slots.length; ++i) {
				if (this.slots[i] != null) {
					NBTTagCompound nbttagcompound1 = new NBTTagCompound();
					nbttagcompound1.setByte("Slot", (byte) i);
					this.slots[i].writeToNBT(nbttagcompound1);
					nbttaglist.appendTag(nbttagcompound1);
				}
			}
			nbt.setTag("Items", nbttaglist);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("Items")) {
			NBTTagList nbttaglist = nbt.getTagList("Items", 10);
			for(int i = 0; i < nbttaglist.tagCount(); ++i) {
				NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
				byte b0 = nbttagcompound1.getByte("Slot");
				if (b0 >= 0 && b0 < this.slots.length) {
					this.slots[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
				}
			}
		}
	}

	@Override
	public int getSizeInventory() {
		if (slots == null) {
			return 0;
		}
		return this.slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if (slots == null) {
			return null;
		}
		return this.slots[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int amount) {
		if (slots == null) {
			return null;
		}
		if (this.slots[index] != null) {
			ItemStack itemstack;
			if (this.slots[index].stackSize <= amount) {
				itemstack = this.slots[index];
				this.slots[index] = null;
				return itemstack;
			} else {
				itemstack = this.slots[index].splitStack(amount);
				if (this.slots[index].stackSize == 0) {
					this.slots[index] = null;
				}
				return itemstack;
			}
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index) {
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
	public void setInventorySlotContents(int index, ItemStack itemstack) {
		if (slots == null) {
			return;
		}
		this.slots[index] = itemstack;
		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		List<Integer> slots = new ArrayList();
		for(int i = 0; i < this.slots.length; i++) {
			if (isInput[i]) {
				slots.add(i);
			}
		}
		int[] slotArray = new int[slots.size()];
		for(int index = 0; index < slotArray.length; index++) {
			slotArray[index] = slots.get(index);
		}
		return slotArray;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, int direction) {
		return insertFilter.isValid(index, stack, module, ForgeDirection.values()[direction]);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, int direction) {
		return extractFilter.isValid(index, stack, module, ForgeDirection.values()[direction]);
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {
		modular.getTile().markDirty();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return modular.getTile().isUseableByPlayer(player);
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return insertFilter.isValid(index, stack, module, ForgeDirection.UNKNOWN);
	}

	@Override
	public String getInventoryName() {
		return inventoryName;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return inventoryName != null;
	}

	@Override
	public boolean isInput(int index) {
		return isInput[index];
	}

	@Override
	public int getInputs() {
		int inputs = 0;
		for(int i = 0; i < this.slots.length; i++) {
			if (this.isInput[i]) {
				inputs++;
			}
		}
		return inputs;
	}

	@Override
	public int getOutputs() {
		int outputs = 0;
		for(int i = 0; i < this.slots.length; i++) {
			if (!this.isInput[i]) {
				outputs++;
			}
		}
		return outputs;
	}

	@Override
	public RecipeItem[] getInputItems() {
		RecipeItem[] inputs = new RecipeItem[getInputs()];
		for(int index = 0; index < getInputs(); index++) {
			ItemStack input = getStackInSlot(index);
			if (input != null) {
				input = input.copy();
			}
			inputs[index] = new RecipeItem(index, input);
		}
		return inputs;
	}

	@Override
	public boolean canRemoveRecipeInputs(int chance, RecipeItem[] inputs) {
		if (inputs != null) {
			for(RecipeItem recipeInput : inputs) {
				if (recipeInput != null) {
					int stackSize = 0;
					if (recipeInput.isOre()) {
						stackSize = recipeInput.ore.stackSize;
					} else if (recipeInput.isItem()) {
						stackSize = recipeInput.item.stackSize;
					} else {
						continue;
					}
					ItemStack itemStack = getStackInSlot(recipeInput.index);
					if (itemStack.stackSize < stackSize) {
						return false;
					}
					continue;
				} else {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean canAddRecipeOutputs(int chance, RecipeItem[] outputs) {
		List<ItemStack> outputStacks = new ArrayList<ItemStack>(getOutputs());
		if(getOutputs() > 0) {
			boolean allFull = true;
			for (int i = getInputs(); i < getInputs() + getOutputs(); i++) {
				ItemStack st = slots[i];
				if(st != null) {
					st = st.copy();
					if(allFull && st.stackSize < st.getMaxStackSize()) {
						allFull = false;
					}
				} else {
					allFull = false;
				}
				outputStacks.add(st);
			}
			if(allFull) {
				return false;
			}
		}

		for (RecipeItem result : outputs) {
			if(result.item != null) {
				if(mergeItemResult(result.item, outputStacks) == 0) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public void removeRecipeInputs(int chance, RecipeItem[] inputs) {
		if (inputs != null) {
			for(RecipeItem recipeInput : inputs) {
				if (recipeInput != null) {
					if (recipeInput.isOre()) {
						decrStackSize(recipeInput.index, recipeInput.ore.stackSize);
					} else if (recipeInput.isItem()) {
						decrStackSize(recipeInput.index, recipeInput.item.stackSize);
					}
				}
			}
		}
	}

	protected int getNumCanMerge(ItemStack itemStack, ItemStack result) {
		if(!itemStack.isItemEqual(result)) {
			return 0;
		}
		return Math.min(itemStack.getMaxStackSize() - itemStack.stackSize, result.stackSize);
	}

	protected int mergeItemResult(ItemStack item, List<ItemStack> outputStacks) {

		int res = 0;

		ItemStack copy = item.copy();
		for (ItemStack outStack : outputStacks) {
			if(outStack != null && copy != null) {
				int num = getNumCanMerge(outStack, copy);
				outStack.stackSize += num;
				res += num;
				copy.stackSize -= num;
				if(copy.stackSize <= 0) {
					return item.stackSize;
				}
			}
		}

		for (int i = 0; i < outputStacks.size(); i++) {
			ItemStack outStack = outputStacks.get(i);
			if(outStack == null) {
				outputStacks.set(i, copy);
				return item.stackSize;
			}
		}

		return 0;
	}

	@Override
	public void addRecipeOutputs(int chance, RecipeItem[] outputs) {
		List<ItemStack> outputStacks = new ArrayList<ItemStack>(getOutputs());
		if(getOutputs() > 0) {
			for (int i = getInputs(); i < getInputs() + getOutputs(); i++) {
				ItemStack it = slots[i];
				if(it != null) {
					it = it.copy();
				}
				outputStacks.add(it);
			}
		}

		for (RecipeItem result : outputs) {
			if(result.item != null) {
				int numMerged = mergeItemResult(result.item, outputStacks);
				if(numMerged > 0) {
					result.item.stackSize -= numMerged;
				}
			}
		}

		if(getOutputs() > 0) {
			int listIndex = 0;
			for (int i = getInputs(); i < getInputs() + getOutputs(); i++) {
				ItemStack st = outputStacks.get(listIndex);
				if(st != null) {
					st = st.copy();
				}
				slots[i] = st;
				listIndex++;
			}
		}
	}

	@Override
	public IContentFilter<ItemStack, M> getInsertFilter() {
		return insertFilter;
	}

	@Override
	public IContentFilter<ItemStack, M> getExtractFilter() {
		return extractFilter;
	}

	@Override
	public IModule getModule() {
		return module;
	}

	@Override
	public String getType() {
		return ModuleManager.inventoryType;
	}
}
