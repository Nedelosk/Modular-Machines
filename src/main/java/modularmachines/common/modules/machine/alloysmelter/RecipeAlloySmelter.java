package modularmachines.common.modules.machine.alloysmelter;

import modularmachines.api.recipes.RecipeItem;
import modularmachines.common.modules.machine.MachineCategorys;
import modularmachines.common.recipes.RecipeHeat;

public class RecipeAlloySmelter extends RecipeHeat {
	
	public RecipeAlloySmelter(RecipeItem inputFirst, RecipeItem inputSecond, RecipeItem[] outputItems, int speed, double heatToRemove, double requiredHeat) {
		super(new RecipeItem[]{inputFirst, inputSecond}, outputItems, speed, heatToRemove, requiredHeat);
	}
	
	public RecipeAlloySmelter(RecipeItem inputFirst, RecipeItem inputSecond, RecipeItem output, int speed, double heatToRemove, double requiredHeat) {
		super(new RecipeItem[]{inputFirst, inputSecond}, new RecipeItem[]{output}, speed, heatToRemove, requiredHeat);
	}
	
	public RecipeAlloySmelter(RecipeItem[] inputItems, RecipeItem[] outputItems, int speed, double heatToRemove,
			double requiredHeat) {
		super(inputItems, outputItems, speed, heatToRemove, requiredHeat);
	}
	
	@Override
	public String getRecipeCategory() {
		return MachineCategorys.ALLOY_SMELTER;
	}
	
}
