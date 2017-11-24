package modularmachines.common.modules.container.components;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.container.ContainerComponent;
import modularmachines.api.modules.listeners.IModuleListener;

public class FluidManager extends ContainerComponent implements IFluidHandler, IModuleListener {
	public final List<IFluidHandler> fluidHandlers;
	private FluidHandlerConcatenate wrapper;
	
	public FluidManager() {
		this.fluidHandlers = new ArrayList<>();
		this.wrapper = new FluidHandlerConcatenate();
	}
	
	@Override
	public void onModuleRemoved(IModule module) {
		IFluidHandler fluidHandler = module.getInterface(IFluidHandler.class);
		if (fluidHandler != null) {
			removeHandler(fluidHandler);
		}
	}
	
	@Override
	public void onModuleAdded(IModule module) {
		IFluidHandler fluidHandler = module.getInterface(IFluidHandler.class);
		if (fluidHandler != null) {
			addHandler(fluidHandler);
		}
	}
	
	public void addHandler(IFluidHandler handler) {
		fluidHandlers.add(handler);
		this.wrapper = new FluidHandlerConcatenate(fluidHandlers.toArray(new IFluidHandler[0]));
	}
	
	public void removeHandler(IFluidHandler handler) {
		fluidHandlers.remove(handler);
		this.wrapper = new FluidHandlerConcatenate(fluidHandlers.toArray(new IFluidHandler[0]));
	}
	
	@Override
	public IFluidTankProperties[] getTankProperties() {
		return wrapper.getTankProperties();
	}
	
	@Override
	public int fill(FluidStack resource, boolean doFill) {
		return wrapper.fill(resource, doFill);
	}
	
	@Nullable
	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		return wrapper.drain(resource, doDrain);
	}
	
	@Nullable
	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return wrapper.drain(maxDrain, doDrain);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && !fluidHandlers.isEmpty();
	}
	
	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ?
				CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this) : null;
	}
}
