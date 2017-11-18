package modularmachines.common.recipes;

import java.util.ArrayList;
import java.util.List;

import modularmachines.api.recipes.IRecipe;
import modularmachines.api.recipes.IRecipeHandler;

public class RecipeHandler<R extends IRecipe> implements IRecipeHandler<R> {
	
	public final String recipeCategory;
	public final List<R> recipes;
	
	public RecipeHandler(String recipeCategory) {
		this.recipeCategory = recipeCategory;
		this.recipes = new ArrayList<>();
	}
	
	@Override
	public boolean addRecipe(R recipe) {
		if (recipe == null) {
			return false;
		}
		if (!isRecipeValid(recipe)) {
			return false;
		}
		return recipes.add(recipe);
	}
	
	@Override
	public boolean removeRecipe(R recipe) {
		return recipes.remove(recipe);
	}
	
	@Override
	public String getRecipeCategory() {
		return recipeCategory;
	}
	
	@Override
	public List<R> getRecipes() {
		return recipes;
	}
	
	@Override
	public boolean isRecipeValid(R recipe) {
		if (recipe.getInputItems() == null || recipe.getInputItems().length == 0) {
			return false;
		}
		if (recipe.getOutputItems() == null || recipe.getOutputItems().length == 0) {
			return false;
		}
		return recipe.getSpeed() != 0;
	}
}
