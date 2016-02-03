package de.nedelosk.forestmods.api.modules.fluids;

import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.utils.ModuleStack;

public interface IModuleWithFluid extends IModule {

	boolean useFluids(ModuleStack stack);

	int getFluidInputs(ModuleStack stack);

	int getFluidOutputs(ModuleStack stack);
}
