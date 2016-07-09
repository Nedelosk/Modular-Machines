package de.nedelosk.modularmachines.common.modules.tools.recipe;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.recipes.IRecipe;
import de.nedelosk.modularmachines.api.recipes.IRecipeBuilder;
import de.nedelosk.modularmachines.api.recipes.IRecipeHandler;
import de.nedelosk.modularmachines.api.recipes.Recipe;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import de.nedelosk.modularmachines.common.recipse.RecipeBuilder;

public class RecipeHandlerDefault extends RecipeHandler{

	public RecipeHandlerDefault(String recipeCategory) {
		super(recipeCategory);
	}

	@Override
	public IRecipe buildDefault() {
		return getDefaultTemplate().build();
	}
}
