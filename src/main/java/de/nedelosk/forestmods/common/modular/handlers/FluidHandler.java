package de.nedelosk.forestmods.common.modular.handlers;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.utils.ModularUtils;
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
		return ModularUtils.getTankManager(machine).getModule().fill(from, resource, doFill, null, machine, false);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return ModularUtils.getTankManager(machine).getModule().drain(from, resource, doDrain, null, machine, false);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return ModularUtils.getTankManager(machine).getModule().drain(from, maxDrain, doDrain, null, machine, false);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return ModularUtils.getTankManager(machine).getModule().canFill(from, fluid, null, machine);
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return ModularUtils.getTankManager(machine).getModule().canDrain(from, fluid, null, machine);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return ModularUtils.getTankManager(machine).getModule().getTankInfo(from, null, machine);
	}
}
