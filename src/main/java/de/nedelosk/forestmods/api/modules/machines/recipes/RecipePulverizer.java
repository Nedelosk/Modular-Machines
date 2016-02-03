package de.nedelosk.forestmods.api.modules.machines.recipes;

import de.nedelosk.forestcore.utils.OreStack;
import de.nedelosk.forestmods.api.recipes.Recipe;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import net.minecraft.item.ItemStack;

public final class RecipePulverizer extends Recipe {

	public RecipePulverizer(ItemStack input, RecipeItem[] output, int speedModifier, int energy) {
		super(new RecipeItem[] { new RecipeItem(input) }, output, speedModifier, energy, "Pulverizer");
	}

	public RecipePulverizer(OreStack input, RecipeItem[] output, int speedModifier, int energy) {
		super(new RecipeItem[] { new RecipeItem(input) }, output, speedModifier, energy, "Pulverizer");
	}
}
