package de.nedelosk.forestmods.common.modular.handlers;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.utils.ModularUtils;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class FluidHandler implements IFluidHandler {

	public IModular modular;

	public FluidHandler(IModular machine) {
		this.modular = machine;
	}

	public void setMachine(IModular machine) {
		this.modular = machine;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return ModularUtils.getTankManager(modular).getModule().fill(from, resource, doFill, null, modular, false);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return ModularUtils.getTankManager(modular).getModule().drain(from, resource, doDrain, null, modular, false);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return ModularUtils.getTankManager(modular).getModule().drain(from, maxDrain, doDrain, null, modular, false);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return ModularUtils.getTankManager(modular).getModule().canFill(from, fluid, null, modular);
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return ModularUtils.getTankManager(modular).getModule().canDrain(from, fluid, null, modular);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return ModularUtils.getTankManager(modular).getModule().getTankInfo(from, null, modular);
	}
}
