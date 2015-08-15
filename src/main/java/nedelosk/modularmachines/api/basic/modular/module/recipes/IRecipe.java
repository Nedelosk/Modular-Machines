package nedelosk.modularmachines.api.basic.modular.module.recipes;

public interface IRecipe {
	
	int[] getModifiers();
	
	int getModifier(int modifierID);
	
	RecipeItem[] getInputs();
	
	RecipeItem[] getOutputs();

	int getRequiredSpeedModifier();
	
	int getRequiredEnergy();
	
	String getRecipeName();
	
}
