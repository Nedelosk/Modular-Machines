package nedelosk.modularmachines.common.modular.module.tool.producer.machine.boiler;

import nedelosk.modularmachines.api.recipes.Recipe;
import nedelosk.modularmachines.api.recipes.RecipeItem;

public class RecipeBurningBoiler extends Recipe {

	public RecipeBurningBoiler(RecipeItem fluid, RecipeItem fuel, RecipeItem[] outputFluid, int speedModifier) {
		super(new RecipeItem[] { fluid, fuel }, outputFluid, speedModifier, 0, "BoilerBurning");
	}

}
