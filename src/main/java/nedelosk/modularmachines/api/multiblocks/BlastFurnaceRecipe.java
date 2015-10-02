package nedelosk.modularmachines.api.multiblocks;

import nedelosk.modularmachines.api.recipes.Recipe;
import nedelosk.modularmachines.api.recipes.RecipeItem;

public class BlastFurnaceRecipe extends Recipe{

	public BlastFurnaceRecipe(RecipeItem[] input, RecipeItem[] output, String recipeName, int heat, int burnTime) {
		super(input, output, 0, 0, recipeName, heat, burnTime);
	}

}
