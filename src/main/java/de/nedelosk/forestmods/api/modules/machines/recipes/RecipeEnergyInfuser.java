package de.nedelosk.forestmods.api.modules.machines.recipes;

import de.nedelosk.forestmods.api.recipes.Recipe;
import de.nedelosk.forestmods.api.recipes.RecipeItem;

public final class RecipeEnergyInfuser extends Recipe {

	public RecipeEnergyInfuser(RecipeItem input1, RecipeItem input2, RecipeItem[] output, int speedModifier, int energy) {
		super(new RecipeItem[] { input1, input2 }, output, speedModifier, energy, "EnergyInfuser");
	}

	public RecipeEnergyInfuser(RecipeItem input, RecipeItem[] output, int speedModifier, int energy) {
		super(new RecipeItem[] { input }, output, speedModifier, energy, "EnergyInfuser");
	}
}
