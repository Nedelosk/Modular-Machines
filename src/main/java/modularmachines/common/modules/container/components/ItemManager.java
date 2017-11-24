package modularmachines.common.modules.container.components;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.container.ContainerComponent;
import modularmachines.api.modules.listeners.IModuleListener;
import modularmachines.common.inventory.CombinedInvWrapper;

public class ItemManager extends ContainerComponent implements IItemHandler, IModuleListener {
	public final List<IItemHandler> itemHandlers;
	private CombinedInvWrapper wrapper;
	
	public ItemManager() {
		this.itemHandlers = new ArrayList<>();
		this.wrapper = new CombinedInvWrapper();
	}
	
	@Override
	public void onModuleRemoved(IModule module) {
		IItemHandler itemHandler = module.getInterface(IItemHandler.class);
		if (itemHandler != null) {
			removeHandler(itemHandler);
		}
	}
	
	@Override
	public void onModuleAdded(IModule module) {
		IItemHandler itemHandler = module.getInterface(IItemHandler.class);
		if (itemHandler != null) {
			addHandler(itemHandler);
		}
	}
	
	public void addHandler(IItemHandler handler) {
		itemHandlers.add(handler);
		this.wrapper = new CombinedInvWrapper(itemHandlers.toArray(new IItemHandler[0]));
	}
	
	public void removeHandler(IItemHandler handler) {
		itemHandlers.remove(handler);
		this.wrapper = new CombinedInvWrapper(itemHandlers.toArray(new IItemHandler[0]));
	}
	
	@Override
	public int getSlots() {
		return wrapper.getSlots();
	}
	
	@Nonnull
	@Override
	public ItemStack getStackInSlot(int slot) {
		return wrapper.getStackInSlot(slot);
	}
	
	@Nonnull
	@Override
	public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
		return wrapper.insertItem(slot, stack, simulate);
	}
	
	@Nonnull
	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		return wrapper.extractItem(slot, amount, simulate);
	}
	
	@Override
	public int getSlotLimit(int slot) {
		return wrapper.getSlotLimit(slot);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && !itemHandlers.isEmpty();
	}
	
	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ?
				CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this) : null;
	}
}
