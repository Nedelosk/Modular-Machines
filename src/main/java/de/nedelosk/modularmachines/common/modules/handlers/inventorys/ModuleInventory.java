package de.nedelosk.modularmachines.common.modules.handlers.inventorys;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularTileEntity;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.api.modules.handlers.IContentFilter;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import de.nedelosk.modularmachines.common.modules.handlers.FilterWrapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.ItemHandlerHelper;

public class ModuleInventory<M extends IModule> implements IModuleInventory<M> {

    protected ItemStack[] stacks;
	protected final boolean[] isInput;
	protected final IModular modular;
	protected final IModuleState<M> state;
	protected final FilterWrapper insertFilter;
	protected final FilterWrapper extractFilter;
	protected final String inventoryName;

	public ModuleInventory(int size, boolean[] inputs, IModular modular, IModuleState<M> state, FilterWrapper insertFilter, FilterWrapper extractFilter, String inventoryName) {
		this.stacks = new ItemStack[size];
		this.isInput = inputs;
		this.modular = modular;
		this.state = state;
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
		Slot slot = container.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (slot instanceof Slot && !(slot instanceof SlotModule)) {
				if (!state.getModule().transferInput(tile, state, player, index, container, itemstack1)) {
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
				slot = container.inventorySlots.get(currentSlot);
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
				slot = container.inventorySlots.get(currentSlot);
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

    public void setSize(int size){
        stacks = new ItemStack[size];
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack){
        validateSlotIndex(slot);
        if (ItemStack.areItemStacksEqual(this.stacks[slot], stack))
            return;
        this.stacks[slot] = stack;
        onContentsChanged(slot);
    }

    @Override
    public int getSlots(){
        return stacks.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot){
        validateSlotIndex(slot);
        return this.stacks[slot];
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate){
        if (stack == null || stack.stackSize == 0)
            return null;
        
        validateSlotIndex(slot);

        ItemStack existing = this.stacks[slot];
        
        if(canInsertItem(slot, existing)){
        	return null;
        }

        int limit = getStackLimit(slot, stack);

        if (existing != null){
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
                return stack;

            limit -= existing.stackSize;
        }

        if (limit <= 0)
            return stack;

        boolean reachedLimit = stack.stackSize > limit;

        if (!simulate){
            if (existing == null){
                this.stacks[slot] = reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack;
            }else{
                existing.stackSize += reachedLimit ? limit : stack.stackSize;
            }
            onContentsChanged(slot);
        }

        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.stackSize - limit) : null;
    }

    @Override
	public ItemStack extractItem(int slot, int amount, boolean simulate){
        if (amount == 0)
            return null;

        validateSlotIndex(slot);

        ItemStack existing = this.stacks[slot];
        
        if(canExtractItem(slot, existing)){
        	return null;
        }

        if (existing == null)
            return null;

        int toExtract = Math.min(amount, existing.getMaxStackSize());

        if (existing.stackSize <= toExtract){
            if (!simulate){
                this.stacks[slot] = null;
                onContentsChanged(slot);
            }
            return existing;
        }else{
            if (!simulate){
                this.stacks[slot] = ItemHandlerHelper.copyStackWithSize(existing, existing.stackSize - toExtract);
                onContentsChanged(slot);
            }

            return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
        }
    }

    protected int getStackLimit(int slot, ItemStack stack){
        return stack.getMaxStackSize();
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbt){
        NBTTagList nbtTagList = new NBTTagList();
        for (int i = 0; i < stacks.length; i++)
        {
            if (stacks[i] != null)
            {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setInteger("Slot", i);
                stacks[i].writeToNBT(itemTag);
                nbtTagList.appendTag(itemTag);
            }
        }
        nbt.setTag("Items", nbtTagList);
        nbt.setInteger("Size", stacks.length);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt){
        setSize(nbt.hasKey("Size", Constants.NBT.TAG_INT) ? nbt.getInteger("Size") : stacks.length);
        NBTTagList tagList = nbt.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.tagCount(); i++)
        {
            NBTTagCompound itemTags = tagList.getCompoundTagAt(i);
            int slot = itemTags.getInteger("Slot");

            if (slot >= 0 && slot < stacks.length)
            {
                stacks[slot] = ItemStack.loadItemStackFromNBT(itemTags);
            }
        }
        onLoad();
    }

    protected void validateSlotIndex(int slot){
        if (slot < 0 || slot >= stacks.length)
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + stacks.length + ")");
    }

    protected void onLoad(){

    }

    protected void onContentsChanged(int slot)
    {

    }

	@Override
	public boolean canInsertItem(int index, ItemStack stack) {
		return insertFilter.isValid(index, stack, state);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack) {
		return extractFilter.isValid(index, stack, state);
	}

	@Override
	public boolean isInput(int index) {
		return isInput[index];
	}

	@Override
	public int getInputs() {
		int inputs = 0;
		for(int i = 0; i < stacks.length; i++) {
			if (this.isInput[i]) {
				inputs++;
			}
		}
		return inputs;
	}

	@Override
	public int getOutputs() {
		int outputs = 0;
		for(int i = 0; i < stacks.length; i++) {
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
				ItemStack st = getStackInSlot(i);
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
						extractItem(recipeInput.index, recipeInput.ore.stackSize, false);
					} else if (recipeInput.isItem()) {
						extractItem(recipeInput.index, recipeInput.item.stackSize, false);
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
				ItemStack it = getStackInSlot(i);
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
				setStackInSlot(i, st);
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
	public IModuleState<M> getModuleState() {
		return state;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return inventoryName != null;
	}

	@Override
	public String getInventoryName() {
		return inventoryName;
	}

	@Override
	public Class<ItemStack> getContentClass() {
		return ItemStack.class;
	}
}
