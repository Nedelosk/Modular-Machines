package modularmachines.api.modules.controller;

import java.util.List;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.state.IModuleState;

public interface IModuleControlled extends IModule {

	IModuleControl getModuleControl(IModuleState state);

	List<IModuleState> getUsedModules(IModuleState state);
}
