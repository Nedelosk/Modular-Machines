package de.nedelosk.forestmods.api.modules.machines.recipe;

import de.nedelosk.forestmods.api.recipes.IMachineMode;

public interface IModuleMachineRecipeMode extends IModuleMachineRecipe {

	Class<? extends IMachineMode> getModeClass();
}
