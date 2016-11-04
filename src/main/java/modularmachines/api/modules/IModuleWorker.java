package modularmachines.api.modules;

import modularmachines.api.modules.state.IModuleState;

public interface IModuleWorker extends IModule {

	boolean isWorking(IModuleState state);
}
