package de.nedelosk.forestmods.api.modules.machines.recipes;

import de.nedelosk.forestmods.api.recipes.Recipe;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import net.minecraft.item.ItemStack;

public final class RecipeSawMill extends Recipe {

	public RecipeSawMill(ItemStack input, RecipeItem[] output, int speedModifier, int energy) {
		super(new RecipeItem[] { new RecipeItem(input) }, output, speedModifier, energy, "SawMill");
	}
}
