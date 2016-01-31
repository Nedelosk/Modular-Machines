package nedelosk.modularmachines.api.modules.fluids;

import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface IModuleWithFluid extends IModule {

	boolean useFluids(ModuleStack stack);

	int getFluidInputs(ModuleStack stack);

	int getFluidOutputs(ModuleStack stack);
}
