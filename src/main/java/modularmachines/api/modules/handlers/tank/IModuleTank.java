package modularmachines.api.modules.handlers.tank;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.handlers.IAdvancedModuleContentHandler;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public interface IModuleTank<M extends IModule> extends IAdvancedModuleContentHandler<FluidStack, M>, IFluidHandler {

	void onChange();

	FluidTankAdvanced getTank(int index);

	FluidTankAdvanced[] getTanks();

	int fillInternal(FluidStack resource, boolean doFill);

	FluidStack drainInternal(FluidStack resource, boolean doDrain);

	FluidStack drainInternal(int maxDrain, boolean doDrain);
}