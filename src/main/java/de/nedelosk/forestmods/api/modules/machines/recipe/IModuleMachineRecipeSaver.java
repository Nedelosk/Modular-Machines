package de.nedelosk.forestmods.api.modules.machines.recipe;

import de.nedelosk.forestmods.api.modules.IModuleSaver;

public interface IModuleMachineRecipeSaver extends IModuleSaver {

	IMachineRecipeHandler getRecipeManager();

	void setRecipeManager(IMachineRecipeHandler manager);
}
