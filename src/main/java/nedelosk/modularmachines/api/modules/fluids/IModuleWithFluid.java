package nedelosk.modularmachines.api.modules.fluids;

import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface IModuleWithFluid<S extends IModuleSaver> extends IModule<S> {

	boolean useFluids(ModuleStack stack);

	int getFluidInputs(ModuleStack stack);

	int getFluidOutputs(ModuleStack stack);
}
