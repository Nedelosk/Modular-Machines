package de.nedelosk.modularmachines.common.modules.tools.recipe;

import de.nedelosk.modularmachines.api.recipes.IRecipe;
import de.nedelosk.modularmachines.api.recipes.Recipe;

public class RecipeHandlerHeat extends RecipeHandler {

	public RecipeHandlerHeat(String recipeCategory) {
		super(recipeCategory);
	}

	@Override
	public IRecipe buildDefault() {
		return getDefaultTemplate().set(Recipe.HEAT, 0D).build();
	}

	@Override
	public boolean isRecipeValid(IRecipe recipe) {
		if (super.isRecipeValid(recipe)) {
			if (recipe.get(Recipe.HEAT) == 0) {
				return false;
			}
			return true;
		}
		return false;
	}
}
