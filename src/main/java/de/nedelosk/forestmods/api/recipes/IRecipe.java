package de.nedelosk.forestmods.api.recipes;

public interface IRecipe {

	RecipeItem[] getInputs();

	RecipeItem[] getOutputs();

	String getRecipeName();

	String getRecipeCategory();

	Object[] getModifiers();

	Object getModifier(int modifierID);

	boolean matches(Object[] craftingModifiers);

	int getRequiredSpeedModifier();

	int getRequiredMaterial();
}
