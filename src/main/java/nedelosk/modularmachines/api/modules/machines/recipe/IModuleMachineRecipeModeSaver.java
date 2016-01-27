package nedelosk.modularmachines.api.modules.machines.recipe;

import nedelosk.modularmachines.api.modules.machines.IModuleMachineSaver;
import nedelosk.modularmachines.api.recipes.IMachineMode;

public interface IModuleMachineRecipeModeSaver extends IModuleMachineSaver {

	IMachineMode getMode();

	void setMode(IMachineMode mode);
}
