package de.nedelosk.modularmachines.api.recipes;

public interface IRecipe {

	RecipeItem[] getInputs();

	RecipeItem[] getOutputs();

	String getRecipeName();

	String getRecipeCategory();

	Object[] getModifiers();

	int getRequiredSpeedModifier();

	int getRequiredMaterial();
}
