package de.nedelosk.forestmods.api.modules.machines.recipe;

import de.nedelosk.forestmods.api.modules.machines.IModuleMachineSaver;
import de.nedelosk.forestmods.api.recipes.IMachineMode;

public interface IModuleMachineRecipeModeSaver extends IModuleMachineSaver {

	IMachineMode getMode();

	void setMode(IMachineMode mode);
}
