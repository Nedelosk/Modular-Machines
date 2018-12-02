package modularmachines.api.recipes;

import java.util.Collection;

public interface IRecipeHandler {
	
	void registerRecipe(IRecipe recipe);
	
	Collection<IRecipe> getRecipes();
}
