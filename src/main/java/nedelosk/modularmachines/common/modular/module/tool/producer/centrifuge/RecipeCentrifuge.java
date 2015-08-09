package nedelosk.modularmachines.common.modular.module.tool.producer.centrifuge;

import nedelosk.modularmachines.api.modular.module.recipes.Recipe;
import nedelosk.modularmachines.api.modular.module.recipes.RecipeItem;

public class RecipeCentrifuge extends Recipe {

	public RecipeCentrifuge(RecipeItem input1, RecipeItem input2, RecipeItem[] output, int speedModifier, int energy) {
		super(new RecipeItem[]{input1, input2}, output, speedModifier, energy, "Centrifuge");
	}
	
	public RecipeCentrifuge(RecipeItem input, RecipeItem[] output, int speedModifier, int energy) {
		super(new RecipeItem[]{input}, output, speedModifier, energy, "Centrifuge");
	}

}
