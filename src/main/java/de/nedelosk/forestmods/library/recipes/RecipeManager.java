package de.nedelosk.forestmods.library.recipes;

import static de.nedelosk.forestmods.library.recipes.RecipeRegistry.registerRecipe;


public final class RecipeManager {

	public static boolean registerAlloySmleterRecipe(String recipeName, RecipeItem input1, RecipeItem input2, RecipeItem output1, RecipeItem output2, int speedModifier, int energy){
		return registerRecipe(new Recipe(recipeName, new RecipeItem[] { input1, input2 }, new RecipeItem[] { output1, output2 }, speedModifier, energy, "AlloySmelter"));
	}

	public static boolean registerPulverizerRecipe(String recipeName, RecipeItem input, RecipeItem output1, RecipeItem output2, int speedModifier, int energy){
		return registerRecipe(new Recipe(recipeName, new RecipeItem[] { input }, new RecipeItem[] { output1, output2 }, speedModifier, energy, "Pulverizer"));
	}

	public static boolean registerSawMillRecipe(String recipeName, RecipeItem input, RecipeItem output1, RecipeItem output2, int speedModifier, int energy){
		return registerRecipe(new Recipe(recipeName, new RecipeItem[] { input }, new RecipeItem[] { output1, output2 }, speedModifier, energy, "SawMill"));
	}

	public static boolean registerBoilerRecipe(String recipeName, RecipeItem input, RecipeItem output, int speedModifier, int heat){
		return registerRecipe(new Recipe(recipeName, new RecipeItem[] { input }, new RecipeItem[] { output }, speedModifier, heat, "Boiler"));
	}

	public static boolean registerBlastFurnaceRecipe(String recipeName, RecipeItem[] input, RecipeItem output0, RecipeItem output1, int speedModifier, int heat){
		return registerRecipe(new Recipe(recipeName, input , new RecipeItem[] { output0, output1 }, speedModifier, heat, "BlastFurnace"));
	}
}
