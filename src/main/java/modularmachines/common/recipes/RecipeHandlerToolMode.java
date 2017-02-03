package modularmachines.common.recipes;

import modularmachines.api.recipes.IRecipeMode;

public class RecipeHandlerToolMode<R extends IRecipeMode> extends RecipeHandler<R> {

	public RecipeHandlerToolMode(String recipeCategory) {
		super(recipeCategory);
	}

	@Override
	public boolean isRecipeValid(R recipe) {
		if (super.isRecipeValid(recipe)) {
			if (recipe.getMode() == null) {
				return false;
			}
			return true;
		}
		return false;
	}
}
