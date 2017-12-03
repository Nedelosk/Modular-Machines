package modularmachines.common.modules.components.handlers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Predicate;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;

import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import modularmachines.api.IOMode;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.components.block.IDropComponent;
import modularmachines.api.modules.components.handlers.IIOComponent;
import modularmachines.api.modules.components.handlers.IItemHandlerComponent;
import modularmachines.api.modules.components.handlers.ISaveHandler;
import modularmachines.common.utils.Log;
import modularmachines.common.utils.ModuleUtil;

public class ItemHandlerComponent extends ItemStackHandler implements IItemHandlerComponent, IDropComponent {
	private final NonNullList<ItemSlot> slots = NonNullList.create();
	@Nullable
	private ISaveHandler<IItemHandlerComponent> saveHandler = null;
	private IModule module;
	
	@SuppressWarnings("unchecked")
	@Override
	public void setSaveHandler(ISaveHandler saveHandler) {
		this.saveHandler = saveHandler;
	}
	
	@Override
	public ISaveHandler<IItemHandlerComponent> getSaveHandler() {
		return saveHandler;
	}
	
	@Override
	public IItemSlot addSlot(int limit, boolean isOutput) {
		ItemSlot slot = new ItemSlot(slots.size(), limit, isOutput);
		slots.add(slot);
		return slot;
	}
	
	@Override
	public void onCreateModule() {
		stacks = NonNullList.withSize(slots.size(), ItemStack.EMPTY);
	}
	
	@Override
	public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
		validateSlotIndex(slot);
		ItemSlot itemSlot = slots.get(slot);
		if (itemSlot.isOutput || !itemSlot.getFilter().test(stack)) {
			return stack;
		}
		return super.insertItem(slot, stack, simulate);
	}
	
	@Override
	public ItemStack insertItemInternal(int slot, ItemStack stack, boolean simulate) {
		return super.insertItem(slot, stack, simulate);
	}
	
	@Override
	public ItemStack extractItemInternal(int slot, int amount, boolean simulate) {
		return super.extractItem(slot, amount, simulate);
	}
	
	@Override
	public void doPull(EnumFacing facing) {
		EnumFacing relativeFacing = facing.getOpposite();
		TileEntity tileEntity = ModuleUtil.getTile(module.getContainer(), facing);
		if (tileEntity == null || !tileEntity.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, relativeFacing)) {
			return;
		}
		IItemHandler itemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, relativeFacing);
		if (itemHandler == null) {
			return;
		}
		move(itemHandler, this);
	}
	
	@Override
	public void doPush(EnumFacing facing) {
		EnumFacing relativeFacing = facing.getOpposite();
		TileEntity tileEntity = ModuleUtil.getTile(module.getContainer(), facing);
		if (tileEntity == null || !tileEntity.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, relativeFacing)) {
			return;
		}
		IItemHandler itemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, relativeFacing);
		if (itemHandler == null) {
			return;
		}
		move(this, itemHandler);
	}
	
	private void move(IItemHandler from, IItemHandler to) {
		for (int i = 0; i < from.getSlots(); i++) {
			ItemStack extractable = from.extractItem(i, Integer.MAX_VALUE, true);
			if (extractable.isEmpty()) {
				continue;
			}
			ItemStack notInserted = ItemHandlerHelper.insertItemStacked(to, extractable, true);
			int insertedAmount = extractable.getCount() - notInserted.getCount();
			if (insertedAmount <= 0) {
				continue;
			}
			ItemStack extracted = from.extractItem(i, insertedAmount, false);
			if (extractable.isEmpty()) {
				continue;
			}
			ItemStack notIn = ItemHandlerHelper.insertItemStacked(to, extracted, false);
			if (!notIn.isEmpty()) {
				ItemStack result = ItemHandlerHelper.insertItem(from, notIn, false);
				if (!result.isEmpty()) {
					Log.err("Failed to insert item: {}.", result);
					//TODO: Drop the Item or something similar.
				}
			}
			return;
		}
	}
	
	@Override
	public int getSlotLimit(int slot) {
		validateSlotIndex(slot);
		ItemSlot itemSlot = slots.get(slot);
		return itemSlot.getLimit();
	}
	
	@Override
	public boolean supportsMode(IOMode ioMode, @Nullable EnumFacing facing) {
		IIOComponent ioComponent = module.getComponent(IIOComponent.class);
		if (ioComponent == null) {
			return true;
		}
		return ioComponent.supportsMode(ioMode, facing);
	}
	
	@Override
	public IOMode getMode(@Nullable EnumFacing facing) {
		IIOComponent ioComponent = module.getComponent(IIOComponent.class);
		if (ioComponent == null) {
			return IOMode.NONE;
		}
		return ioComponent.getMode(facing);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		deserializeNBT(compound.getCompoundTag("Items"));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("Items", serializeNBT());
		return compound;
	}
	
	@Nullable
	@Override
	public IItemSlot getSlot(int index) {
		if (index >= slots.size()) {
			return null;
		}
		return slots.get(index);
	}
	
	@Override
	public void setProvider(IModule provider) {
		this.module = provider;
	}
	
	@Override
	public IModule getProvider() {
		return module;
	}
	
	@Override
	protected void onContentsChanged(int slot) {
		super.onContentsChanged(slot);
		ModuleUtil.markDirty(module);
		ModuleUtil.markForModelUpdate(module);
	}
	
	@Override
	public void addDrops(NonNullList<ItemStack> drops) {
		for (ItemStack stack : stacks) {
			if (!stack.isEmpty()) {
				drops.add(stack);
			}
		}
	}
	
	public class ItemSlot implements IItemSlot {
		private final int index;
		private final int limit;
		private final boolean isOutput;
		private Predicate<ItemStack> filter = i -> true;
		@Nullable
		private String backgroundTexture = null;
		
		private ItemSlot(int index, int limit, boolean isOutput) {
			this.index = index;
			this.limit = limit;
			this.isOutput = isOutput;
		}
		
		@Override
		public IItemSlot setBackgroundTexture(String backgroundTexture) {
			this.backgroundTexture = backgroundTexture;
			return this;
		}
		
		@Override
		public String getBackgroundTexture() {
			return backgroundTexture;
		}
		
		@Override
		public int getIndex() {
			return index;
		}
		
		@Override
		public IItemSlot setFilter(Predicate<ItemStack> filter) {
			this.filter = filter;
			return this;
		}
		
		@Override
		public Predicate<ItemStack> getFilter() {
			return filter;
		}
		
		@Override
		public ItemStack getItem() {
			return getStackInSlot(index);
		}
		
		@Override
		public int getLimit() {
			return limit;
		}
		
		@Override
		public boolean isOutput() {
			return isOutput;
		}
	}
}
