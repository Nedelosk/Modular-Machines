package modularmachines.common.inventory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.items.IItemHandler;

import modularmachines.api.EnumIOMode;
import modularmachines.api.IIOConfigurable;
import modularmachines.api.modules.components.IItemHandlerComponent;

public class ItemHandlerWrapper implements IItemHandler, IIOConfigurable {
	private final IItemHandlerComponent[] itemHandler;
	private final int[] baseIndex;
	private final int slotCount;
	@Nullable
	private final EnumFacing facing;
	
	public ItemHandlerWrapper(Collection<IItemHandlerComponent> itemHandlers, @Nullable EnumFacing facing) {
		this.itemHandler = itemHandlers.toArray(new IItemHandlerComponent[itemHandlers.size()]);
		this.baseIndex = new int[itemHandler.length];
		int index = 0;
		for (int i = 0; i < itemHandler.length; i++) {
			index += itemHandler[i].getSlots();
			baseIndex[i] = index;
		}
		this.slotCount = index;
		this.facing = facing;
	}
	
	private int getIndexForSlot(int slot) {
		if (slot < 0) {
			return -1;
		}
		
		for (int i = 0; i < baseIndex.length; i++) {
			if (slot - baseIndex[i] < 0) {
				return i;
			}
		}
		return -1;
	}
	
	@Nullable
	private IItemHandlerComponent getHandlerFromIndex(int index) {
		if (index < 0 || index >= itemHandler.length) {
			return null;
		}
		return itemHandler[index];
	}
	
	private int getSlotFromIndex(int slot, int index) {
		if (index <= 0 || index >= baseIndex.length) {
			return slot;
		}
		return slot - baseIndex[index - 1];
	}
	
	@Override
	public int getSlots() {
		return slotCount;
	}
	
	@Override
	public boolean supportsMode(EnumIOMode ioMode, @Nullable EnumFacing facing) {
		return ioMode != EnumIOMode.DISABLED && getMode(facing) != EnumIOMode.DISABLED;
	}
	
	@Override
	public EnumIOMode getMode(@Nullable EnumFacing facing) {
		for (IIOConfigurable component : itemHandler) {
			if (component.getMode(facing) != EnumIOMode.DISABLED) {
				return EnumIOMode.NONE;
			}
		}
		return EnumIOMode.DISABLED;
	}
	
	@Override
	public ItemStack getStackInSlot(int slot) {
		int index = getIndexForSlot(slot);
		IItemHandler handler = getHandlerFromIndex(index);
		slot = getSlotFromIndex(slot, index);
		if (handler == null) {
			return ItemStack.EMPTY;
		}
		return handler.getStackInSlot(slot);
	}
	
	@Override
	public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
		int index = getIndexForSlot(slot);
		IItemHandlerComponent handler = getHandlerFromIndex(index);
		if (handler == null || !handler.supportsMode(EnumIOMode.INPUT, facing)) {
			return stack;
		}
		slot = getSlotFromIndex(slot, index);
		return handler.insertItem(slot, stack, simulate);
	}
	
	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		int index = getIndexForSlot(slot);
		IItemHandlerComponent handler = getHandlerFromIndex(index);
		slot = getSlotFromIndex(slot, index);
		if (handler == null || !handler.supportsMode(EnumIOMode.OUTPUT, facing)) {
			return ItemStack.EMPTY;
		}
		return handler.extractItem(slot, amount, simulate);
	}
	
	@Override
	public int getSlotLimit(int slot) {
		int index = getIndexForSlot(slot);
		IItemHandler handler = getHandlerFromIndex(index);
		int localSlot = getSlotFromIndex(slot, index);
		if (handler == null) {
			return 0;
		}
		return handler.getSlotLimit(localSlot);
	}
}
