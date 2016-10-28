package de.nedelosk.modularmachines.api.recipes;

import de.nedelosk.modularmachines.api.property.PropertyToolMode;
import net.minecraft.item.ItemStack;

public class RecipeUtil {

	public static final PropertyToolMode LATHEMODE = new PropertyToolMode("mode", LatheModes.class, LatheModes.ROD);

	public static boolean addPulverizer(String recipeName, ItemStack input, RecipeItem[] output,
			int speed/* , double kinetic */) {
		return addPulverizer(recipeName, new RecipeItem(input), output, speed/* , kinetic */);
	}

	public static boolean addPulverizer(String recipeName, OreStack input, RecipeItem[] output,
			int speed/* , double kinetic */) {
		return addPulverizer(recipeName, new RecipeItem(input), output, speed/* , kinetic */);
	}

	public static boolean addPulverizer(String recipeName, RecipeItem input, RecipeItem[] output,
			int speed/* , double kinetic */) {
		IRecipeHandler handler = RecipeRegistry.getRecipeHandler("Pulverizer");
		IRecipeBuilder builder = handler.getDefaultTemplate();
		builder.set(Recipe.INPUTS, new RecipeItem[] { input }).set(Recipe.OUTPUTS, output).set(Recipe.SPEED, speed)
		/* .set(Recipe.KINETIC, kinetic) */;
		return handler.registerRecipe(builder.build());
	}

	public static boolean addAlloySmelter(String recipeName, RecipeItem inputFirst, RecipeItem inputSecond, RecipeItem[] output, int speed, double heat, double heatToRemove) {
		IRecipeHandler handler = RecipeRegistry.getRecipeHandler("AlloySmelter");
		IRecipeBuilder builder = handler.getDefaultTemplate();
		builder.set(Recipe.INPUTS, new RecipeItem[] { inputFirst, inputSecond }).set(Recipe.OUTPUTS, output).set(Recipe.SPEED, speed).set(Recipe.HEAT, heat).set(Recipe.HEATTOREMOVE, heatToRemove);
		return handler.registerRecipe(builder.build());
	}

	public static boolean addLathe(String recipeName, RecipeItem input, RecipeItem output, int speed, LatheModes mode) {
		IRecipeHandler handler = RecipeRegistry.getRecipeHandler("Lathe");
		IRecipeBuilder builder = handler.getDefaultTemplate();
		builder.set(Recipe.INPUTS, new RecipeItem[] { input }).set(Recipe.OUTPUTS, new RecipeItem[] { output }).set(Recipe.SPEED, speed).set(LATHEMODE, mode);
		return handler.registerRecipe(builder.build());
	}

	/*
	 * public static boolean addSawMill(String recipeName, OreStack input,
	 * RecipeItem[] output, int speedModifier, int energy){ return
	 * registerRecipe(new Recipe(recipeName, new RecipeItem[] { new
	 * RecipeItem(input) }, output, speedModifier, energy, "SawMill")); } public
	 * static boolean addSawMill(String recipeName, ItemStack input,
	 * RecipeItem[] output, int speedModifier, int energy){ return
	 * registerRecipe(new Recipe(recipeName, new RecipeItem[] { new
	 * RecipeItem(input) }, output, speedModifier, energy, "SawMill")); } public
	 * static boolean addSawMill(String recipeName, RecipeItem input,
	 * RecipeItem[] output, int speedModifier, int energy){ return
	 * registerRecipe(new Recipe(recipeName, new RecipeItem[] { input }, output,
	 * speedModifier, energy, "SawMill")); }
	 */
	public static boolean addBoilerRecipe(String recipeName, RecipeItem input, RecipeItem output, int speed, double heat, double heatToRemove) {
		IRecipeHandler handler = RecipeRegistry.getRecipeHandler("Boiler");
		IRecipeBuilder builder = handler.getDefaultTemplate();
		builder.set(Recipe.INPUTS, new RecipeItem[] { input }).set(Recipe.OUTPUTS, new RecipeItem[] { output }).set(Recipe.SPEED, speed).set(Recipe.HEAT, heat).set(Recipe.HEATTOREMOVE, heatToRemove);
		return handler.registerRecipe(builder.build());
	}

	public static enum LatheModes implements IToolMode {
		ROD("rod"), WIRE("wire"), SCREW("screw");

		private String name;

		private LatheModes(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	}
}