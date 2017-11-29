package modularmachines.common.modules.container.components;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;

import modularmachines.api.IOMode;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.components.handlers.IFluidHandlerComponent;
import modularmachines.api.modules.container.ContainerComponent;
import modularmachines.api.modules.container.IModuleListener;
import modularmachines.common.tanks.FluidHandlerWrapper;

public class FluidManager extends ContainerComponent implements IFluidHandler, IModuleListener {
	private final Set<IFluidHandlerComponent> fluidHandlers = new HashSet<>();
	private final Map<EnumFacing, FluidHandlerWrapper> facingHandlers = new EnumMap<>(EnumFacing.class);
	private FluidHandlerConcatenate internal;
	@Nullable
	private FluidHandlerWrapper wrapper;
	
	public FluidManager() {
		this.internal = new FluidHandlerConcatenate();
	}
	
	@Override
	public void onModuleRemoved(IModule module) {
		IFluidHandlerComponent fluidHandler = module.getComponent(IFluidHandlerComponent.class);
		if (fluidHandler != null) {
			removeHandler(fluidHandler);
		}
	}
	
	@Override
	public void onModuleAdded(IModule module) {
		IFluidHandlerComponent fluidHandler = module.getComponent(IFluidHandlerComponent.class);
		if (fluidHandler != null) {
			addHandler(fluidHandler);
		}
	}
	
	private void addHandler(IFluidHandlerComponent handler) {
		fluidHandlers.add(handler);
		reset();
	}
	
	private void removeHandler(IFluidHandlerComponent handler) {
		fluidHandlers.remove(handler);
		reset();
	}
	
	private void reset() {
		facingHandlers.clear();
		wrapper = null;
		this.internal = new FluidHandlerConcatenate(fluidHandlers.toArray(new IFluidHandler[fluidHandlers.size()]));
	}
	
	@Override
	public IFluidTankProperties[] getTankProperties() {
		return internal.getTankProperties();
	}
	
	@Override
	public int fill(FluidStack resource, boolean doFill) {
		return internal.fill(resource, doFill);
	}
	
	@Nullable
	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		return internal.drain(resource, doDrain);
	}
	
	@Nullable
	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return internal.drain(maxDrain, doDrain);
	}
	
	private FluidHandlerWrapper getWrapper() {
		if (wrapper == null) {
			wrapper = new FluidHandlerWrapper(fluidHandlers, null);
		}
		return wrapper;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		if (capability != CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || fluidHandlers.isEmpty()) {
			return false;
		}
		FluidHandlerWrapper handler = facing == null ? getWrapper() : facingHandlers.computeIfAbsent(facing, k -> new FluidHandlerWrapper(fluidHandlers, facing));
		return handler.supportsMode(IOMode.NONE, facing);
	}
	
	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability != CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return null;
		}
		IFluidHandler handler = facing == null ? getWrapper() : facingHandlers.computeIfAbsent(facing, k -> new FluidHandlerWrapper(fluidHandlers, facing));
		return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(handler);
	}
}
