package de.nedelosk.forestmods.library.modules.recipes;

import de.nedelosk.forestmods.library.recipes.Recipe;
import de.nedelosk.forestmods.library.recipes.RecipeItem;

public final class RecipeEnergyInfuser extends Recipe {

	public RecipeEnergyInfuser(String recipeName, RecipeItem[] inputs, RecipeItem[] outputs, int speedModifier, int energy, String recipeCategory,
			Object[] objects) {
		super(recipeName, inputs, outputs, speedModifier, energy, recipeCategory, objects);
	}

	public RecipeEnergyInfuser(String recipeName, RecipeItem input1, RecipeItem input2, RecipeItem[] output, int speedModifier, int energy) {
		super(recipeName, new RecipeItem[] { input1, input2 }, output, speedModifier, energy, "EnergyInfuser");
	}

	public RecipeEnergyInfuser(String recipeName, RecipeItem input, RecipeItem[] output, int speedModifier, int energy) {
		super(recipeName, new RecipeItem[] { input }, output, speedModifier, energy, "EnergyInfuser");
	}
}
