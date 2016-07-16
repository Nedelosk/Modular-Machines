package de.nedelosk.modularmachines.api.modules;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleController extends IModule {

	int getAllowedComplexity(IModuleState state);

}
