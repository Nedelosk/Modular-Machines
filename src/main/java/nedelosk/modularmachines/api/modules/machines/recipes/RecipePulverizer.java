package nedelosk.modularmachines.api.modules.machines.recipes;

import nedelosk.forestday.api.crafting.OreStack;
import nedelosk.modularmachines.api.recipes.Recipe;
import nedelosk.modularmachines.api.recipes.RecipeItem;
import net.minecraft.item.ItemStack;

public final class RecipePulverizer extends Recipe {

	public RecipePulverizer(ItemStack input, RecipeItem[] output, int speedModifier, int energy) {
		super(new RecipeItem[] { new RecipeItem(input) }, output, speedModifier, energy, "Pulverizer");
	}

	public RecipePulverizer(OreStack input, RecipeItem[] output, int speedModifier, int energy) {
		super(new RecipeItem[] { new RecipeItem(input) }, output, speedModifier, energy, "Pulverizer");
	}
}
