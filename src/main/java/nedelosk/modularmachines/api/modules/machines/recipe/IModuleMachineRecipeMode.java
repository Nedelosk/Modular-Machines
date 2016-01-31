package nedelosk.modularmachines.api.modules.machines.recipe;

import nedelosk.modularmachines.api.recipes.IMachineMode;

public interface IModuleMachineRecipeMode<S extends IModuleMachineRecipeModeSaver> extends IModuleMachineRecipe<S> {

	Class<? extends IMachineMode> getModeClass();
}
