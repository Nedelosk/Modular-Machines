package de.nedelosk.modularmachines.common.modules.tools.recipe;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.recipes.IRecipe;
import de.nedelosk.modularmachines.api.recipes.IRecipeBuilder;
import de.nedelosk.modularmachines.api.recipes.IRecipeHandler;
import de.nedelosk.modularmachines.api.recipes.Recipe;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import de.nedelosk.modularmachines.common.recipse.RecipeBuilder;

public abstract class RecipeHandler implements IRecipeHandler {

	public final String recipeCategory;
	public final List<IRecipe> recipes;

	public RecipeHandler(String recipeCategory) {
		this.recipeCategory = recipeCategory;
		this.recipes = new ArrayList<>();
	}

	@Override
	public IRecipeBuilder createBuilder() {
		return new RecipeBuilder();
	}

	@Override
	public IRecipeBuilder getDefaultTemplate() {
		IRecipeBuilder builder = createBuilder();
		return builder.set(Recipe.CATEGORY, recipeCategory).set(Recipe.NAME, "Default").set(Recipe.INPUTS, new RecipeItem[0]).set(Recipe.OUTPUTS, new RecipeItem[0]).set(Recipe.SPEED, 0);
	}

	@Override
	public boolean registerRecipe(IRecipe recipe) {
		if (recipe == null) {
			return false;
		}
		try {
			if (!isRecipeValid(recipe)) {
				return false;
			}
		} catch (Exception exception) {
			return false;
		}
		for(int index = 0; index < recipe.getInputs().length; index++) {
			recipe.getInputs()[index].index = index;
		}
		for(int index = 0; index < recipe.getOutputs().length; index++) {
			recipe.getOutputs()[index].index = index;
		}
		return recipes.add(recipe);
	}

	@Override
	public boolean removeRecipe(IRecipe recipe) {
		if (recipes.contains(recipe)) {
			return false;
		}
		return recipes.remove(recipe);
	}

	@Override
	public String getRecipeCategory() {
		return recipeCategory;
	}

	@Override
	public List<IRecipe> getRecipes() {
		return recipes;
	}

	@Override
	public boolean isRecipeValid(IRecipe recipe) {
		if (recipe.getInputs() == null || recipe.getInputs().length == 0) {
			return false;
		}
		if (recipe.getOutputs() == null || recipe.getOutputs().length == 0) {
			return false;
		}
		if (recipe.getRecipeName() == null) {
			return false;
		}
		if (recipe.getRecipeCategory() == null) {
			return false;
		}
		if (recipe.getSpeed() == 0) {
			return false;
		}
		return true;
	}
}
