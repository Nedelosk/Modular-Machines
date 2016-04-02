package de.nedelosk.forestmods.api.modules.producers.recipes;

import de.nedelosk.forestcore.utils.OreStack;
import de.nedelosk.forestmods.api.recipes.Recipe;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import net.minecraft.item.ItemStack;

public final class RecipePulverizer extends Recipe {

	public RecipePulverizer(String recipeName, RecipeItem[] inputs, RecipeItem[] outputs, int speedModifier, int energy, String recipeCategory,
			Object[] objects) {
		super(recipeName, inputs, outputs, speedModifier, energy, recipeCategory, objects);
	}

	public RecipePulverizer(String recipeName, ItemStack input, RecipeItem[] output, int speedModifier, int energy) {
		super(recipeName, new RecipeItem[] { new RecipeItem(input) }, output, speedModifier, energy, "Pulverizer");
	}

	public RecipePulverizer(String recipeName, OreStack input, RecipeItem[] output, int speedModifier, int energy) {
		super(recipeName, new RecipeItem[] { new RecipeItem(input) }, output, speedModifier, energy, "Pulverizer");
	}
}
