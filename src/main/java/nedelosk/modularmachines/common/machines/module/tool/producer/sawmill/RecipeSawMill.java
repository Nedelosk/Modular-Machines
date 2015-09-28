package nedelosk.modularmachines.common.machines.module.tool.producer.sawmill;

import nedelosk.modularmachines.api.basic.machine.module.recipes.Recipe;
import nedelosk.modularmachines.api.basic.machine.module.recipes.RecipeItem;
import net.minecraft.item.ItemStack;

public class RecipeSawMill extends Recipe {

	public RecipeSawMill(ItemStack input, RecipeItem[] output, int speedModifier, int energy) {
		super(new RecipeItem[]{new RecipeItem(input)}, output, speedModifier, energy, "SawMill");
	}

}
