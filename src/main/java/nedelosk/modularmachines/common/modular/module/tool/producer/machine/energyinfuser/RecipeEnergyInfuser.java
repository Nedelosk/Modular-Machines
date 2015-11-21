package nedelosk.modularmachines.common.modular.module.tool.producer.machine.energyinfuser;

import nedelosk.modularmachines.api.recipes.Recipe;
import nedelosk.modularmachines.api.recipes.RecipeItem;

public class RecipeEnergyInfuser extends Recipe {

	public RecipeEnergyInfuser(RecipeItem input1, RecipeItem input2, RecipeItem[] output, int speedModifier, int energy) {
		super(new RecipeItem[] { input1, input2 }, output, speedModifier, energy, "EnergyInfuser");
	}

	public RecipeEnergyInfuser(RecipeItem input, RecipeItem[] output, int speedModifier, int energy) {
		super(new RecipeItem[] { input }, output, speedModifier, energy, "EnergyInfuser");
	}

}
