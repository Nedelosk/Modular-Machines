package de.nedelosk.modularmachines.api.recipes;

import java.util.List;

public interface IRecipeProvider {

	IRecipe getCurrentRecipe();

	List<IRecipe> getRecipes();

	IRecipe getValidRecipe(Object... ingredients);
}
