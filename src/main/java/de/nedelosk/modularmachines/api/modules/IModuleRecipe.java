package de.nedelosk.modularmachines.api.modules;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.recipes.IRecipe;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;

public interface IModuleRecipe extends IModule {

	/**
	 * @return The current recipe of the module state.
	 */
	IRecipe getCurrentRecipe(IModuleState state);

	/**
	 * Set the current recipe of the module state.
	 */
	void setCurrentRecipe(IModuleState state, IRecipe recipe);

	/**
	 * @return True if the stack is a input, of a recipe, at the matching position.
	 */
	boolean isRecipeInput(IModuleState state, RecipeItem item);

}
