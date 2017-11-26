package modularmachines.common.modules.components;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Predicate;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;

import net.minecraftforge.items.ItemStackHandler;

import modularmachines.api.EnumIOMode;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.components.IIOComponent;
import modularmachines.api.modules.components.IItemHandlerComponent;
import modularmachines.common.utils.ModuleUtil;

public class ItemHandlerComponent extends ItemStackHandler implements IItemHandlerComponent {
	private final NonNullList<ItemSlot> slots = NonNullList.create();
	private IModule module;
	
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
	public int getSlotLimit(int slot) {
		validateSlotIndex(slot);
		ItemSlot itemSlot = slots.get(slot);
		return itemSlot.getLimit();
	}
	
	@Override
	public boolean supportsMode(EnumIOMode ioMode, @Nullable EnumFacing facing) {
		IIOComponent ioComponent = module.getComponent(IIOComponent.class);
		if (ioComponent == null) {
			return true;
		}
		return ioComponent.supportsMode(ioMode, facing);
	}
	
	@Override
	public EnumIOMode getMode(@Nullable EnumFacing facing) {
		IIOComponent ioComponent = module.getComponent(IIOComponent.class);
		if (ioComponent == null) {
			return EnumIOMode.NONE;
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
	}
}
