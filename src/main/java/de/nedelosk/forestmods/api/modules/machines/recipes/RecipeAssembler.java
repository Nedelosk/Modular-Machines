package de.nedelosk.forestmods.api.modules.machines.recipes;

import de.nedelosk.forestmods.api.recipes.Recipe;
import de.nedelosk.forestmods.api.recipes.RecipeItem;

public final class RecipeAssembler extends Recipe {

	public RecipeAssembler(RecipeItem input1, RecipeItem input2, RecipeItem[] output, int speedModifier, int energy) {
		super(new RecipeItem[] { input1, input2 }, output, speedModifier, energy, "Assembler");
	}

	public RecipeAssembler(RecipeItem input1, RecipeItem input2, RecipeItem output, int speedModifier, int energy) {
		this(input1, input2, new RecipeItem[] { output }, speedModifier, energy);
	}
}
