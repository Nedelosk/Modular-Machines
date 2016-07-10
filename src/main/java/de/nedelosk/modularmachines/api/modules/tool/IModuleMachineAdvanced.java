package de.nedelosk.modularmachines.api.modules.tool;

import de.nedelosk.modularmachines.api.modules.IModuleState;
import de.nedelosk.modularmachines.api.recipes.IToolMode;

public interface IModuleMachineAdvanced extends IModuleMachine {

	Class<? extends IToolMode> getModeClass();

	IToolMode getCurrentMode(IModuleState state);

	void setCurrentMode(IModuleState state, IToolMode mode);
}
