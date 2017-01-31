package modularmachines.api.recipes;

public interface IRecipeConsumer {

	int getInputCount();

	int getOutputCount();
	
	RecipeItem[] getInputs();

	boolean extractInputs(float chance, IRecipe recipe, boolean simulate);

	boolean insertOutputs(float chance, IRecipe recipe, boolean simulate);
	
}
