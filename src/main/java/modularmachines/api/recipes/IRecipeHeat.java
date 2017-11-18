package modularmachines.api.recipes;

public interface IRecipeHeat extends IRecipe {
	
	double getHeatToRemove();
	
	double getRequiredHeat();
	
}
