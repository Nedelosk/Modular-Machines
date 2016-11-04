package modularmachines.api.modules.controller;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.properties.IModuleControllerProperties;
import modularmachines.api.modules.state.IModuleState;

public interface IModuleController extends IModule, IModuleControllerProperties {

	boolean canWork(IModuleState controllerState, IModuleState moduleState);
}
