package modularmachines.common.recipes;

import modularmachines.api.recipes.IRecipeHeat;
import modularmachines.api.recipes.RecipeItem;

public abstract class RecipeHeat extends Recipe implements IRecipeHeat {

	protected final double heatToRemove;
	protected final double requiredHeat;
	
	public RecipeHeat(RecipeItem[] inputItems, RecipeItem[] outputItems, int speed, double heatToRemove, double requiredHeat) {
		super(inputItems, outputItems, speed);
		this.heatToRemove = heatToRemove;
		this.requiredHeat = requiredHeat;
	}

	@Override
	public double getHeatToRemove() {
		return heatToRemove;
	}

	@Override
	public double getRequiredHeat() {
		return requiredHeat;
	}

}
