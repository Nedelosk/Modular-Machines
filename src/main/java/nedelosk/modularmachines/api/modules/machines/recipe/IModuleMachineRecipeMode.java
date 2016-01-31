package nedelosk.modularmachines.api.modules.machines.recipe;

import nedelosk.modularmachines.api.recipes.IMachineMode;

public interface IModuleMachineRecipeMode extends IModuleMachineRecipe {

	Class<? extends IMachineMode> getModeClass();
}
