package modularmachines.api.modules;

import javax.annotation.Nonnull;

import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.recipes.IRecipe;
import modularmachines.api.recipes.RecipeItem;

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
	 * @return True if the stack is a input, of a recipe, at the matching
	 *         position.
	 */
	boolean isRecipeInput(IModuleState state, @Nonnull RecipeItem item);
}
