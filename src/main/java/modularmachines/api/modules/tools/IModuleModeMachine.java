package modularmachines.api.modules.tools;

import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.recipes.IToolMode;

public interface IModuleModeMachine extends IModuleMachine {

	IToolMode getMode(int index);

	IToolMode getNextMode(IModuleState state);

	IToolMode getCurrentMode(IModuleState state);

	void setCurrentMode(IModuleState state, IToolMode mode);
}
