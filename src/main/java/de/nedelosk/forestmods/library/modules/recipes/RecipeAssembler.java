package de.nedelosk.forestmods.library.modules.recipes;

import de.nedelosk.forestmods.library.recipes.Recipe;
import de.nedelosk.forestmods.library.recipes.RecipeItem;

public final class RecipeAssembler extends Recipe {

	public RecipeAssembler(String recipeName, RecipeItem[] inputs, RecipeItem[] outputs, int speedModifier, int energy, String recipeCategory,
			Object[] objects) {
		super(recipeName, inputs, outputs, speedModifier, energy, recipeCategory, objects);
	}

	public RecipeAssembler(String recipeName, RecipeItem input1, RecipeItem input2, RecipeItem[] output, int speedModifier, int energy) {
		super(recipeName, new RecipeItem[] { input1, input2 }, output, speedModifier, energy, "Assembler");
	}

	public RecipeAssembler(String recipeName, RecipeItem input1, RecipeItem input2, RecipeItem output, int speedModifier, int energy) {
		this(recipeName, input1, input2, new RecipeItem[] { output }, speedModifier, energy);
	}
}
