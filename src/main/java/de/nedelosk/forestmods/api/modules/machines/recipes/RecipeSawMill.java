package de.nedelosk.forestmods.api.modules.machines.recipes;

import de.nedelosk.forestcore.utils.OreStack;
import de.nedelosk.forestmods.api.recipes.Recipe;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import net.minecraft.item.ItemStack;

public final class RecipeSawMill extends Recipe {

	public RecipeSawMill(String recipeName, RecipeItem[] inputs, RecipeItem[] outputs, int speedModifier, int energy, String recipeCategory, Object[] objects) {
		super(recipeName, inputs, outputs, speedModifier, energy, recipeCategory, objects);
	}

	public RecipeSawMill(String recipeName, ItemStack input, RecipeItem[] output, int speedModifier, int energy) {
		super(recipeName, new RecipeItem[] { new RecipeItem(input) }, output, speedModifier, energy, "SawMill");
	}

	public RecipeSawMill(String recipeName, OreStack input, RecipeItem[] output, int speedModifier, int energy) {
		super(recipeName, new RecipeItem[] { new RecipeItem(input) }, output, speedModifier, energy, "SawMill");
	}
}
