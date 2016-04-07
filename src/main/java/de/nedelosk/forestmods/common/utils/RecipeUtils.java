package de.nedelosk.forestmods.common.utils;

import de.nedelosk.forestcore.utils.OreStack;
import de.nedelosk.forestmods.api.modules.recipes.RecipeAlloySmelter;
import de.nedelosk.forestmods.api.modules.recipes.RecipeAssembler;
import de.nedelosk.forestmods.api.modules.recipes.RecipeCentrifuge;
import de.nedelosk.forestmods.api.modules.recipes.RecipeLathe;
import de.nedelosk.forestmods.api.modules.recipes.RecipeLathe.LatheModes;
import de.nedelosk.forestmods.api.modules.recipes.RecipePulverizer;
import de.nedelosk.forestmods.api.modules.recipes.RecipeSawMill;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import de.nedelosk.forestmods.api.recipes.RecipeRegistry;
import net.minecraft.item.ItemStack;

public class RecipeUtils {

	public static void addAlloySmelter(String recipeName, RecipeItem input1, RecipeItem input2, RecipeItem[] output, int speedModifier, int energy) {
		RecipeRegistry.registerRecipe(new RecipeAlloySmelter(recipeName, input1, input2, output, speedModifier, energy));
	}

	public static void addCentrifuge(String recipeName, RecipeItem input1, RecipeItem input2, RecipeItem[] output, int speedModifier, int energy) {
		RecipeRegistry.registerRecipe(new RecipeCentrifuge(recipeName, input1, input2, output, speedModifier, energy));
	}

	public static void addCentrifuge(String recipeName, RecipeItem input, RecipeItem[] output, int speedModifier, int energy) {
		RecipeRegistry.registerRecipe(new RecipeCentrifuge(recipeName, input, output, speedModifier, energy));
	}

	public static void addAssembler(String recipeName, RecipeItem input1, RecipeItem input2, RecipeItem[] output, int speedModifier, int energy) {
		RecipeRegistry.registerRecipe(new RecipeAssembler(recipeName, input1, input2, output, speedModifier, energy));
	}

	public static void addAssembler(String recipeName, RecipeItem input1, RecipeItem input2, RecipeItem output, int speedModifier, int energy) {
		RecipeRegistry.registerRecipe(new RecipeAssembler(recipeName, input1, input2, output, speedModifier, energy));
	}

	public static void addLathe(String recipeName, RecipeItem input, RecipeItem output0, int speedModifier, int energy, LatheModes mode) {
		RecipeRegistry.registerRecipe(new RecipeLathe(recipeName, input, output0, speedModifier, energy, mode));
	}

	public static void addLathe(String recipeName, RecipeItem input, RecipeItem output0, RecipeItem output1, int speedModifier, int energy, LatheModes mode) {
		RecipeRegistry.registerRecipe(new RecipeLathe(recipeName, input, output0, output1, speedModifier, energy, mode));
	}

	public static void addPulverizer(String recipeName, ItemStack input, RecipeItem[] output, int speedModifier, int energy) {
		RecipeRegistry.registerRecipe(new RecipePulverizer(recipeName, input, output, speedModifier, energy));
	}

	public static void addPulverizer(String recipeName, OreStack input, RecipeItem[] output, int speedModifier, int energy) {
		RecipeRegistry.registerRecipe(new RecipePulverizer(recipeName, input, output, speedModifier, energy));
	}

	public static void addSawMill(String recipeName, ItemStack input, RecipeItem[] output, int speedModifier, int energy) {
		RecipeRegistry.registerRecipe(new RecipeSawMill(recipeName, input, output, speedModifier, energy));
	}

	public static void addSawMill(String recipeName, OreStack input, RecipeItem[] output, int speedModifier, int energy) {
		RecipeRegistry.registerRecipe(new RecipeSawMill(recipeName, input, output, speedModifier, energy));
	}
}
