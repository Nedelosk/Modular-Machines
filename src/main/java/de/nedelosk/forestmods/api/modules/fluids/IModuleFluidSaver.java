package de.nedelosk.forestmods.api.modules.fluids;

import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.utils.ModuleStack;

public interface IModuleFluidSaver extends IModuleSaver {

	int getFluidInputs(ModuleStack stack);

	int getFluidOutputs(ModuleStack stack);
}
