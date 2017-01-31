package modularmachines.api.recipes;

public interface IRecipe {

	RecipeItem[] getInputItems();
	
	RecipeItem[] getOutputItems();

	String getRecipeCategory();

	int getSpeed();
}
