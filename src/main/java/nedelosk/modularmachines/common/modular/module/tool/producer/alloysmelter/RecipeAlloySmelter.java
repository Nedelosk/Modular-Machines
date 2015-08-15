package nedelosk.modularmachines.common.modular.module.tool.producer.alloysmelter;

import nedelosk.modularmachines.api.basic.modular.module.recipes.Recipe;
import nedelosk.modularmachines.api.basic.modular.module.recipes.RecipeItem;

public class RecipeAlloySmelter extends Recipe {

	public RecipeAlloySmelter(RecipeItem input1, RecipeItem input2, RecipeItem[] output, int speedModifier, int energy) {
		super(new RecipeItem[]{input1, input2}, output, speedModifier, energy, "AlloySmelter");
	}

}
