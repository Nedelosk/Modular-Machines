package nedelosk.modularmachines.api.recipes;

public interface IRecipe {

	Object[] getModifiers();

	Object getModifier(int modifierID);

	RecipeItem[] getInputs();

	RecipeItem[] getOutputs();

	int getRequiredSpeedModifier();

	int getRequiredMaterial();

	String getRecipeName();

}
