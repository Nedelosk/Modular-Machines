package de.nedelosk.forestmods.api.recipes;

public interface IRecipe {

	Object[] getModifiers();

	Object getModifier(int modifierID);

	RecipeItem[] getInputs();

	RecipeItem[] getOutputs();

	int getRequiredSpeedModifier();

	boolean matches(Object[] craftingModifiers);

	int getRequiredMaterial();

	String getRecipeName();

	String getRecipeCategory();
}
