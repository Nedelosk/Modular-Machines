package de.nedelosk.modularmachines.api.modules.tool;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.recipes.IMachineMode;

public interface IModuleToolAdvanced extends IModuleTool {

	Class<? extends IMachineMode> getModeClass();

	IMachineMode getCurrentMode(IModuleState state);
	
	IMachineMode getDefaultMode(IModuleState state);

	void setCurrentMode(IModuleState state, IMachineMode mode);
}
