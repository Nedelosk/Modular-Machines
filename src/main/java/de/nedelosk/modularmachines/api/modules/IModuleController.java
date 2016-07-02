package de.nedelosk.modularmachines.api.modules;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleController extends IModule {

	int getAllowedModuleComplexity(Class<? extends IModule> moduleClass, IModuleState state);

	int getAllowedComplexity(IModuleState state);

}
