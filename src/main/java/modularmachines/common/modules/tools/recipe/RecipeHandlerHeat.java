package modularmachines.common.modules.tools.recipe;

import modularmachines.api.recipes.IRecipe;
import modularmachines.api.recipes.Recipe;

public class RecipeHandlerHeat extends RecipeHandler {

	public RecipeHandlerHeat(String recipeCategory) {
		super(recipeCategory);
	}

	@Override
	public IRecipe buildDefault() {
		return getDefaultTemplate().setValue(Recipe.HEAT, 0D).init();
	}

	@Override
	public boolean isRecipeValid(IRecipe recipe) {
		if (super.isRecipeValid(recipe)) {
			if (recipe.getValue(Recipe.HEAT) == 0) {
				return false;
			}
			return true;
		}
		return false;
	}
}
