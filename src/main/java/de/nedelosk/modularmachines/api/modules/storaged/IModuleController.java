package de.nedelosk.modularmachines.api.modules.storaged;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleController extends IModule {

	int getAllowedComplexity(IModuleContainer container);

	boolean canWork(IModuleState controllerState, IModuleState moduleState);

}
