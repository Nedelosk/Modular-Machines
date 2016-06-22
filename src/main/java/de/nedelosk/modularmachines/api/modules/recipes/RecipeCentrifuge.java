package de.nedelosk.modularmachines.api.modules.recipes;

import de.nedelosk.modularmachines.api.recipes.Recipe;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;

public final class RecipeCentrifuge extends Recipe {

	public RecipeCentrifuge(String recipeName, RecipeItem[] inputs, RecipeItem[] outputs, int speedModifier, int energy, String recipeCategory,
			Object[] objects) {
		super(recipeName, inputs, outputs, speedModifier, energy, recipeCategory, objects);
	}

	public RecipeCentrifuge(String recipeName, RecipeItem input1, RecipeItem input2, RecipeItem[] output, int speedModifier, int energy) {
		super(recipeName, new RecipeItem[] { input1, input2 }, output, speedModifier, energy, "Centrifuge");
	}

	public RecipeCentrifuge(String recipeName, RecipeItem input, RecipeItem[] output, int speedModifier, int energy) {
		super(recipeName, new RecipeItem[] { input }, output, speedModifier, energy, "Centrifuge");
	}
}
