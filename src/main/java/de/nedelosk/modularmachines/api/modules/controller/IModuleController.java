package de.nedelosk.modularmachines.api.modules.controller;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.properties.IModuleControllerProperties;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleController extends IModule, IModuleControllerProperties {

	boolean canWork(IModuleState controllerState, IModuleState moduleState);
}
