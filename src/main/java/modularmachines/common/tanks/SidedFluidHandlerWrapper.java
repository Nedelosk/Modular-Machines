package modularmachines.common.tanks;

import javax.annotation.Nullable;

import net.minecraft.util.EnumFacing;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import modularmachines.api.EnumIOMode;
import modularmachines.api.modules.components.IIOComponent;

public class SidedFluidHandlerWrapper implements IFluidHandler {
	@Nullable
	private final EnumFacing facing;
	private final IIOComponent ioComponent;
	private final IFluidHandler fluidHandler;
	
	public SidedFluidHandlerWrapper(@Nullable EnumFacing facing, IIOComponent ioComponent, IFluidHandler fluidHandler) {
		this.facing = facing;
		this.ioComponent = ioComponent;
		this.fluidHandler = fluidHandler;
	}
	
	@Override
	public IFluidTankProperties[] getTankProperties() {
		return fluidHandler.getTankProperties();
	}
	
	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if (!ioComponent.supportsMode(facing, EnumIOMode.INPUT)) {
			return 0;
		}
		return fluidHandler.fill(resource, doFill);
	}
	
	@Nullable
	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if (!ioComponent.supportsMode(facing, EnumIOMode.OUTPUT)) {
			return null;
		}
		return fluidHandler.drain(resource, doDrain);
	}
	
	@Nullable
	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		if (!ioComponent.supportsMode(facing, EnumIOMode.OUTPUT)) {
			return null;
		}
		return fluidHandler.drain(maxDrain, doDrain);
	}
}
