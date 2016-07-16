package de.nedelosk.modularmachines.common.modules.storaged.tools.recipe;

import de.nedelosk.modularmachines.api.recipes.IRecipe;

public class RecipeHandlerDefault extends RecipeHandler{

	public RecipeHandlerDefault(String recipeCategory) {
		super(recipeCategory);
	}

	@Override
	public IRecipe buildDefault() {
		return getDefaultTemplate().build();
	}
}
