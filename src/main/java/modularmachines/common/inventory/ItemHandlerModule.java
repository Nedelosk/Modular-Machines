package modularmachines.common.inventory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;

import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

import modularmachines.api.components.EnumComponentTag;
import modularmachines.api.components.IComponent;
import modularmachines.api.components.IComponentTag;
import modularmachines.api.modules.Module;
import modularmachines.api.recipes.IRecipe;
import modularmachines.api.recipes.IRecipeConsumer;
import modularmachines.api.recipes.RecipeItem;
import modularmachines.common.modules.ModuleComponent;
import modularmachines.common.utils.ItemUtil;

import static net.minecraft.item.ItemStack.EMPTY;

public class ItemHandlerModule extends ModuleComponent implements IItemHandlerModifiable, IRecipeConsumer, IComponent {
	
	protected final NonNullList<ItemContainer> containers;
	
	public ItemHandlerModule(Module module) {
		super(module);
		this.containers = NonNullList.create();
	}
	
	public ItemContainer addSlot(boolean isInput) {
		ItemContainer container = new ItemContainer(containers.size(), isInput, module);
		containers.add(container);
		return container;
	}
	
	public ItemContainer addContainer(boolean isInput, String backgroundTexture) {
		ItemContainer container = new ItemContainer(containers.size(), isInput, module, backgroundTexture);
		containers.add(container);
		return container;
	}
	
	@Override
	public IComponentTag[] getTags() {
		return new IComponentTag[]{EnumComponentTag.ITEMS};
	}
	
	/* INEVNTORY */
	@Override
	public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
		validateSlotIndex(slot);
		if (ItemStack.areItemStacksEqual(this.containers.get(slot).get(), stack)) {
			return;
		}
		this.containers.get(slot).set(stack);
		onContentsChanged(slot);
	}
	
	@Override
	public int getSlots() {
		return containers.size();
	}
	
	@Override
	@Nonnull
	public ItemStack getStackInSlot(int slot) {
		validateSlotIndex(slot);
		return this.containers.get(slot).get();
	}
	
	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		if (amount <= 0) {
			return EMPTY;
		}
		validateSlotIndex(slot);
		if (!isValid(slot, containers.get(slot).get())) {
			return EMPTY;
		}
		return extractItemInternal(slot, amount, simulate);
	}
	
	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if (stack.isEmpty()) {
			return EMPTY;
		}
		validateSlotIndex(slot);
		if (!isValid(slot, stack)) {
			return stack;
		}
		return insertItemInternal(slot, stack, simulate);
	}
	
	@Nonnull
	public ItemStack insertItemInternal(int slot, @Nonnull ItemStack stack, boolean simulate) {
		if (stack.isEmpty()) {
			return ItemStack.EMPTY;
		}
		
		validateSlotIndex(slot);
		
		ItemContainer container = containers.get(slot);
		ItemStack existing = container.get();
		
		int limit = getStackLimit(slot, stack);
		
		if (!existing.isEmpty()) {
			if (!ItemHandlerHelper.canItemStacksStack(stack, existing)) {
				return stack;
			}
			
			limit -= existing.getCount();
		}
		
		if (limit <= 0) {
			return stack;
		}
		
		boolean reachedLimit = stack.getCount() > limit;
		
		if (!simulate) {
			if (existing.isEmpty()) {
				container.set(reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
			} else {
				ItemUtil.grow(existing, reachedLimit ? limit : stack.getCount());
			}
			onContentsChanged(slot);
		}
		
		return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
	}
	
	@Nonnull
	public ItemStack extractItemInternal(int slot, int amount, boolean simulate) {
		if (amount <= 0) {
			return ItemStack.EMPTY;
		}
		
		validateSlotIndex(slot);
		
		ItemContainer container = containers.get(slot);
		ItemStack existing = container.get();
		
		if (existing.isEmpty()) {
			return ItemStack.EMPTY;
		}
		
		int toExtract = Math.min(amount, existing.getMaxStackSize());
		
		if (existing.getCount() <= toExtract) {
			if (!simulate) {
				container.set(ItemStack.EMPTY);
				onContentsChanged(slot);
			}
			return existing;
		} else {
			if (!simulate) {
				container.set(ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract));
				onContentsChanged(slot);
			}
			
			return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
		}
	}
	
	@Override
	public int getSlotLimit(int slot) {
		return 64;
	}
	
	protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
		return Math.min(getSlotLimit(slot), stack.getMaxStackSize());
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagList nbtTagList = new NBTTagList();
		for (int i = 0; i < containers.size(); i++) {
			if (ItemUtil.isNotEmpty(containers.get(i).get())) {
				NBTTagCompound itemTag = new NBTTagCompound();
				itemTag.setInteger("Slot", i);
				containers.get(i).get().writeToNBT(itemTag);
				nbtTagList.appendTag(itemTag);
			}
		}
		compound.setTag("Items", nbtTagList);
		return compound;
	}
	
	public void readFromNBT(NBTTagCompound compound) {
		NBTTagList tagList = compound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound itemTags = tagList.getCompoundTagAt(i);
			int slot = itemTags.getInteger("Slot");
			
			if (slot >= 0 && slot < containers.size()) {
				containers.get(slot).set(new ItemStack(itemTags));
			}
		}
		onLoad();
	}

	/*
	 * public void addToolTip(List<String> tooltip, ItemStack stack, Module
	 * state) {
	 * tooltip.add(I18n.translateToLocal("mm.tooltip.handler.inventorys")); for
	 * (int i = 0; i < getCollection(); i++) { ItemStack itemStack =
	 * getStackInSlot(i); if (itemStack != null) { tooltip.add(" " +
	 * TextFormatting.ITALIC +
	 * I18n.translateToLocal("mm.tooltip.handler.inventory") + " " + i);
	 * tooltip.add(" - " +
	 * I18n.translateToLocal("mm.tooltip.handler.inventory.item") +
	 * itemStack.getDisplayName() + ", " +
	 * I18n.translateToLocal("mm.tooltip.handler.inventory.amount") +
	 * itemStack.stackSize); } } }
	 */
	
	protected void validateSlotIndex(int slot) {
		if (slot < 0 || slot >= containers.size()) {
			throw new RuntimeException("Slot " + slot + " not in valid range - [0," + containers.size() + ")");
		}
	}
	
	protected void onLoad() {
	}
	
	protected void onContentsChanged(int slot) {
	}
	
	public boolean isValid(int index, ItemStack stack) {
		return containers.get(index).isValid(stack);
	}
	
	public boolean isInput(int index) {
		if (containers.size() <= index) {
			return false;
		}
		return containers.get(index).isInput();
	}
	
	@Override
	public int getInputCount() {
		int inputCount = 0;
		for (ItemContainer container : containers) {
			if (container.isInput()) {
				inputCount++;
			}
		}
		return inputCount;
	}
	
	@Override
	public int getOutputCount() {
		int outputCount = 0;
		for (ItemContainer container : containers) {
			if (!container.isInput()) {
				outputCount++;
			}
		}
		return outputCount;
	}
	
	@Override
	public RecipeItem[] getInputs() {
		int count = getInputCount();
		RecipeItem[] inputs = new RecipeItem[count];
		for (int index = 0; index < count; index++) {
			ItemStack input = getStackInSlot(index);
			if (input != null) {
				input = input.copy();
			}
			inputs[index] = new RecipeItem(input);
		}
		return inputs;
	}
	
	@Override
	public boolean insertOutputs(float chance, IRecipe recipe, boolean simulate) {
		if (recipe == null) {
			return false;
		}
		InventoryManipulator manipulator = new InventoryManipulator(this);
		for (RecipeItem recipeOutput : recipe.getOutputItems()) {
			if (recipeOutput != null) {
				if (recipeOutput.isItem() && recipeOutput.canUseItem(chance)) {
					if (simulate) {
						if (!manipulator.tryAddStack(recipeOutput.item).isEmpty()) {
							return false;
						}
					} else {
						if (!manipulator.addStack(recipeOutput.item).isEmpty()) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean extractInputs(float chance, IRecipe recipe, boolean simulate) {
		if (recipe == null) {
			return false;
		}
		RecipeItem[] items = recipe.getInputItems();
		for (int i = 0; i < items.length; i++) {
			RecipeItem recipeInput = items[i];
			if (recipeInput != null) {
				if (recipeInput.isOre()) {
					if (extractItem(i, recipeInput.ore.stackSize, simulate).isEmpty()) {
						return false;
					}
				} else if (recipeInput.isItem()) {
					if (extractItem(i, recipeInput.item.getCount(), simulate).isEmpty()) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public Module getModule() {
		return module;
	}
	
	@Nullable
	public ItemContainer getContainer(int index) {
		if (containers.size() <= index) {
			return null;
		}
		return containers.get(index);
	}
	
	public List<ItemStack> getDrops() {
		List<ItemStack> drops = new ArrayList<>();
		for (int i = 0; i < getSlots(); i++) {
			ItemStack stack = getStackInSlot(i);
			if (stack != null) {
				drops.add(stack);
			}
		}
		return drops;
	}

	/*@Override
	public void cleanHandler(IModuleState state) {
		IModularHandler handler = state.getModular().getHandler();
		if (handler instanceof IModularHandlerTileEntity) {
			IModularHandlerTileEntity tileHandler = (IModularHandlerTileEntity) handler;
			List<IItemHandler> handlers = new ArrayList<>();
			for (IModuleState moduleState : state.getModuleHandler().getModules()) {
				if (moduleState.getContentHandler(IItemHandler.class) != null) {
					handlers.add(moduleState.getContentHandler(IItemHandler.class));
				}
				for (IModulePage page : (List<IModulePage>) moduleState.getPages()) {
					if (page.getContentHandler(IItemHandler.class) != null) {
						handlers.add(page.getContentHandler(IItemHandler.class));
					}
				}
			}
			for (EnumFacing facing : EnumFacing.VALUES) {
				TileEntity tile = tileHandler.getWorld().getTileEntity(tileHandler.getPos().offset(facing));
				if (tile != null
						&& tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite())) {
					handlers.add(
							tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite()));
				}
			}
			for (IItemHandler itemHandler : handlers) {
				if (!isEmpty()) {
					for (int i = 0; i < getCollection(); i++) {
						setStackInSlot(i, ItemHandlerHelper.insertItem(itemHandler, getStackInSlot(i), false));
					}
				}
			}
		}
	}

	@Override
	public boolean isCleanable() {
		return true;
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack stack : stacks) {
			if (stack != null && stack.getItem() != null && stack.stackSize > 0) {
				return false;
			}
		}
		return true;
	}*/
}
