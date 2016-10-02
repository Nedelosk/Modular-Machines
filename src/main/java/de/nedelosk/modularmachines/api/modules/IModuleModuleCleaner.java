package de.nedelosk.modularmachines.api.modules;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleModuleCleaner extends ITickable {

	void cleanModule(IModuleState state);
}
