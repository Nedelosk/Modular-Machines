package modularmachines.common.recipes;

import modularmachines.api.recipes.IRecipeHeat;

public class RecipeHandlerHeat<R extends IRecipeHeat> extends RecipeHandler<R> {
	
	public RecipeHandlerHeat(String recipeCategory) {
		super(recipeCategory);
	}
	
	@Override
	public boolean isRecipeValid(R recipe) {
		if (super.isRecipeValid(recipe)) {
			return !(recipe.getRequiredHeat() <= 0);
		}
		return false;
	}
}
