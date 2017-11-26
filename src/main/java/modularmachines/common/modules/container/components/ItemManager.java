package modularmachines.common.modules.container.components;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import modularmachines.api.EnumIOMode;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.components.IItemHandlerComponent;
import modularmachines.api.modules.container.ContainerComponent;
import modularmachines.api.modules.listeners.IModuleListener;
import modularmachines.common.inventory.CombinedInvWrapper;
import modularmachines.common.inventory.ItemHandlerWrapper;

public class ItemManager extends ContainerComponent implements IItemHandler, IModuleListener {
	public final List<IItemHandlerComponent> itemHandlers;
	private final Map<EnumFacing, ItemHandlerWrapper> facingHandlers = new EnumMap<>(EnumFacing.class);
	private CombinedInvWrapper internal;
	@Nullable
	private ItemHandlerWrapper wrapper;
	
	public ItemManager() {
		this.itemHandlers = new ArrayList<>();
		this.internal = new CombinedInvWrapper();
	}
	
	@Override
	public void onModuleRemoved(IModule module) {
		IItemHandlerComponent itemHandler = module.getComponent(IItemHandlerComponent.class);
		if (itemHandler != null) {
			removeHandler(itemHandler);
		}
	}
	
	@Override
	public void onModuleAdded(IModule module) {
		IItemHandlerComponent itemHandler = module.getComponent(IItemHandlerComponent.class);
		if (itemHandler != null) {
			addHandler(itemHandler);
		}
	}
	
	private void addHandler(IItemHandlerComponent handler) {
		itemHandlers.add(handler);
		reset();
	}
	
	private void removeHandler(IItemHandlerComponent handler) {
		itemHandlers.remove(handler);
		reset();
	}
	
	private void reset() {
		itemHandlers.clear();
		facingHandlers.clear();
		wrapper = null;
		this.internal = new CombinedInvWrapper(itemHandlers.toArray(new IItemHandler[0]));
	}
	
	@Override
	public int getSlots() {
		return internal.getSlots();
	}
	
	@Nonnull
	@Override
	public ItemStack getStackInSlot(int slot) {
		return internal.getStackInSlot(slot);
	}
	
	@Nonnull
	@Override
	public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
		return internal.insertItem(slot, stack, simulate);
	}
	
	@Nonnull
	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		return internal.extractItem(slot, amount, simulate);
	}
	
	@Override
	public int getSlotLimit(int slot) {
		return internal.getSlotLimit(slot);
	}
	
	private ItemHandlerWrapper getWrapper() {
		if (wrapper == null) {
			wrapper = new ItemHandlerWrapper(itemHandlers, null);
		}
		return wrapper;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		if (capability != CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || itemHandlers.isEmpty()) {
			return false;
		}
		ItemHandlerWrapper handler = facing == null ? getWrapper() : facingHandlers.computeIfAbsent(facing, k -> new ItemHandlerWrapper(itemHandlers, facing));
		return handler.supportsMode(EnumIOMode.NONE, facing);
	}
	
	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability != CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return null;
		}
		ItemHandlerWrapper handler = facing == null ? getWrapper() : facingHandlers.computeIfAbsent(facing, k -> new ItemHandlerWrapper(itemHandlers, facing));
		return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(handler);
	}
}
