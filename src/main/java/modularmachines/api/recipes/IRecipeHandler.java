package modularmachines.api.recipes;

import java.util.List;

public interface IRecipeHandler<R extends IRecipe> {

	/**
	 * It is called before the registry register a recipe.
	 */
	boolean isRecipeValid(R recipe);

	boolean addRecipe(R recipe);

	boolean removeRecipe(R recipe);

	List<R> getRecipes();

	String getRecipeCategory();
}
