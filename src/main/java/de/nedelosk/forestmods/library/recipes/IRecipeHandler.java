package de.nedelosk.forestmods.library.recipes;

public interface IRecipeHandler {

	IRecipeJsonSerializer getJsonSerialize();

	IRecipeNBTSerializer getNBTSerialize();

	boolean matches(Object[] craftingModifiers);

	String getRecipeCategory();
}
