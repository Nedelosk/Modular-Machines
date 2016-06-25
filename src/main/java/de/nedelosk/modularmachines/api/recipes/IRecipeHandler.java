package de.nedelosk.modularmachines.api.recipes;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IRecipeHandler {

	IRecipeJsonSerializer getJsonSerialize();

	IRecipeNBTSerializer getNBTSerialize();

	boolean matches(IRecipe recipe, Object[] craftingModifiers);

	String getRecipeCategory();
}
