package modularmachines.api.modules;

import modularmachines.api.modules.state.IModuleState;

public interface IModuleModuleCleaner extends ITickable, IModule {

	void cleanModule(IModuleState state);
}
