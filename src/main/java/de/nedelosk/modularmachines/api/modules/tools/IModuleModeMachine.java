package de.nedelosk.modularmachines.api.modules.tools;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.recipes.IToolMode;

public interface IModuleModeMachine extends IModuleMachine {

	IToolMode getMode(int index);

	IToolMode getNextMode(IModuleState state);

	IToolMode getCurrentMode(IModuleState state);

	void setCurrentMode(IModuleState state, IToolMode mode);
}
