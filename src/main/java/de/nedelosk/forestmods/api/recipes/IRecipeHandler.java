package de.nedelosk.forestmods.api.recipes;

public interface IRecipeHandler {

	IRecipeJsonSerializer getJsonSerialize();

	IRecipeNBTSerializer getNBTSerialize();

	String getRecipeCategory();

	Class<? extends IRecipe> getRecipeClass();
}
