package de.nedelosk.modularmachines.api.modules;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleWorker extends IModule {

	boolean isWorking(IModuleState state);
}
