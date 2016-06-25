package de.nedelosk.modularmachines.api.recipes;

public interface IRecipeHandler {

	IRecipeJsonSerializer getJsonSerialize();

	IRecipeNBTSerializer getNBTSerialize();

	boolean matches(IRecipe recipe, Object[] craftingModifiers);

	String getRecipeCategory();
}
