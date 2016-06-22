package de.nedelosk.modularmachines.api.modules.handlers.tank;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public interface IModuleTank<M extends IModule> extends IModuleContentHandler<FluidStack, M> {

	ITankData getTank(int index);

	ITankData[] getTanks();

	int fill(EnumFacing from, FluidStack resource, boolean doFill, boolean fillOutput);

	FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain, boolean drainInput);

	FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain, boolean drainInput);

	boolean canFill(EnumFacing from, Fluid fluid);

	boolean canDrain(EnumFacing from, Fluid fluid);

	FluidTankInfo[] getTankInfo(EnumFacing from);
}
