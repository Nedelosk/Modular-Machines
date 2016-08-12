package de.nedelosk.modularmachines.api.modules;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleModuleCleaner extends IModuleTickable {
	
	void cleanModule(IModuleState state);
}
