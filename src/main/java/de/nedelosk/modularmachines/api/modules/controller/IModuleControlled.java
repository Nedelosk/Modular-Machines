package de.nedelosk.modularmachines.api.modules.controller;

import java.util.List;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleControlled extends IModule {

	IModuleControl getModuleControl(IModuleState state);

	List<IModuleState> getUsedModules(IModuleState state);
}
