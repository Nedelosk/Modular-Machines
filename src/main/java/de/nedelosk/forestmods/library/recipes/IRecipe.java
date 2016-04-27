package de.nedelosk.forestmods.library.recipes;

public interface IRecipe {

	RecipeItem[] getInputs();

	RecipeItem[] getOutputs();

	String getRecipeName();

	String getRecipeCategory();

	Object[] getModifiers();

	int getRequiredSpeedModifier();

	int getRequiredMaterial();
}
