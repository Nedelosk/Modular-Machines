package de.nedelosk.forestmods.api.modules.handlers.tank;

import de.nedelosk.forestmods.api.modules.handlers.IModuleContentHandler;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public interface IModuleTank extends IModuleContentHandler<FluidStack> {

	ITankData getTank(int index);

	ITankData[] getTanks();

	int fill(ForgeDirection from, FluidStack resource, boolean doFill, boolean fillOutput);

	FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain, boolean drainInput);

	FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain, boolean drainInput);

	boolean canFill(ForgeDirection from, Fluid fluid);

	boolean canDrain(ForgeDirection from, Fluid fluid);

	FluidTankInfo[] getTankInfo(ForgeDirection from);
}
