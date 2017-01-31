package modularmachines.common.recipes;

import java.util.List;

import modularmachines.api.recipes.IRecipe;

public interface IRecipeProvider {

	IRecipe getCurrentRecipe();

	List<IRecipe> getRecipes();

	IRecipe getValidRecipe(Object... ingredients);
}
