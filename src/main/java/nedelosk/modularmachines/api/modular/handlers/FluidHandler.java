package nedelosk.modularmachines.api.modular.handlers;

import nedelosk.modularmachines.api.modular.IModular;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class FluidHandler implements IFluidHandler {

	public IModular machine;

	public FluidHandler(IModular machine) {
		this.machine = machine;
	}

	public void setMachine(IModular machine) {
		this.machine = machine;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return machine.getTankManeger().getStack().getModule().fill(from, resource, doFill, null, machine, false);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return machine.getTankManeger().getStack().getModule().drain(from, resource, doDrain, null, machine, false);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return machine.getTankManeger().getStack().getModule().drain(from, maxDrain, doDrain, null, machine, false);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return machine.getTankManeger().getStack().getModule().canFill(from, fluid, null, machine);
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return machine.getTankManeger().getStack().getModule().canDrain(from, fluid, null, machine);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return machine.getTankManeger().getStack().getModule().getTankInfo(from, null, machine);
	}
}
