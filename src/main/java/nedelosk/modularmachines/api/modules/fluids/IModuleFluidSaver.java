package nedelosk.modularmachines.api.modules.fluids;

import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface IModuleFluidSaver extends IModuleSaver {

	int getFluidInputs(ModuleStack stack);

	int getFluidOutputs(ModuleStack stack);
}
