package de.nedelosk.modularmachines.api.modules.storaged;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleController extends IModule {

	boolean canWork(IModuleState controllerState, IModuleState moduleState);

}
