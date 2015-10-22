package nedelosk.modularmachines.common.modular.module.tool.producer.machine.pulverizer;

import nedelosk.modularmachines.api.recipes.Recipe;
import nedelosk.modularmachines.api.recipes.RecipeItem;
import nedelosk.nedeloskcore.api.crafting.OreStack;
import net.minecraft.item.ItemStack;

public class RecipePulverizer extends Recipe {

	public RecipePulverizer(ItemStack input, RecipeItem[] output, int speedModifier, int energy) {
		super(new RecipeItem[]{new RecipeItem(input)}, output, speedModifier, energy, "Pulverizer");
	}
	
	public RecipePulverizer(OreStack input, RecipeItem[] output, int speedModifier, int energy) {
		super(new RecipeItem[]{new RecipeItem(input)}, output, speedModifier, energy, "Pulverizer");
	}

}
