package de.nedelosk.modularmachines.api.recipes;

import java.util.List;

public interface IRecipeHandler {

	IRecipeBuilder createBuilder();

	/**
	 * To read recipe form NBT or Json data's.
	 */
	IRecipe buildDefault();

	/**
	 * It is called before the registry register a recipe.
	 */
	boolean isRecipeValid(IRecipe recipe);

	boolean registerRecipe(IRecipe recipe);

	boolean removeRecipe(IRecipe recipe);

	List<IRecipe> getRecipes();

	String getRecipeCategory();

	IRecipeBuilder getDefaultTemplate();
}
