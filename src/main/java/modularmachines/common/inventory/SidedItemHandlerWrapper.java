package modularmachines.common.inventory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.items.IItemHandler;

import modularmachines.api.EnumIOMode;
import modularmachines.api.modules.components.IIOComponent;

public class SidedItemHandlerWrapper implements IItemHandler {
	@Nullable
	private final EnumFacing facing;
	private final IIOComponent ioComponent;
	private final IItemHandler itemHandler;
	
	public SidedItemHandlerWrapper(@Nullable EnumFacing facing, IIOComponent ioComponent, IItemHandler itemHandler) {
		this.facing = facing;
		this.ioComponent = ioComponent;
		this.itemHandler = itemHandler;
	}
	
	@Override
	public int getSlots() {
		return itemHandler.getSlots();
	}
	
	@Nonnull
	@Override
	public ItemStack getStackInSlot(int slot) {
		return itemHandler.getStackInSlot(slot);
	}
	
	@Nonnull
	@Override
	public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
		if (!ioComponent.supportsMode(facing, EnumIOMode.INPUT)) {
			return stack;
		}
		return itemHandler.insertItem(slot, stack, simulate);
	}
	
	@Nonnull
	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		if (!ioComponent.supportsMode(facing, EnumIOMode.OUTPUT)) {
			return ItemStack.EMPTY;
		}
		return itemHandler.extractItem(slot, amount, simulate);
	}
	
	@Override
	public int getSlotLimit(int slot) {
		return itemHandler.getSlotLimit(slot);
	}
}
