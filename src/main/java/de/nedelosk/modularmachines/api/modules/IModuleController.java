package de.nedelosk.modularmachines.api.modules;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleController extends IModule, IModuleControllerProperties {

	boolean canWork(IModuleState controllerState, IModuleState moduleState);

}
