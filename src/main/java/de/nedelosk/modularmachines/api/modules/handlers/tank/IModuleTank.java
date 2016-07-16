package de.nedelosk.modularmachines.api.modules.handlers.tank;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandlerAdvanced;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public interface IModuleTank<M extends IModule> extends IModuleContentHandlerAdvanced<FluidStack, M>, IFluidHandler {

	void onChange();

	FluidTankAdvanced getTank(int index);

	FluidTankAdvanced[] getTanks();

	int fillInternal(FluidStack resource, boolean doFill);

	FluidStack drainInternal(FluidStack resource, boolean doDrain);

	FluidStack drainInternal(int maxDrain, boolean doDrain);
}
