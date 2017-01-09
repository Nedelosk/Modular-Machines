package modularmachines.api.recipes;

import net.minecraft.item.ItemStack;

import modularmachines.api.property.PropertyToolMode;

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
		builder.setValue(Recipe.INPUTS, new RecipeItem[] { input }).setValue(Recipe.OUTPUTS, output).setValue(Recipe.SPEED, speed)
		/* .set(Recipe.KINETIC, kinetic) */;
		return handler.registerRecipe(builder.init());
	}

	public static boolean addAlloySmelter(String recipeName, RecipeItem inputFirst, RecipeItem inputSecond, RecipeItem[] output, int speed, double heat, double heatToRemove) {
		IRecipeHandler handler = RecipeRegistry.getRecipeHandler("AlloySmelter");
		IRecipeBuilder builder = handler.getDefaultTemplate();
		builder.setValue(Recipe.INPUTS, new RecipeItem[] { inputFirst, inputSecond }).setValue(Recipe.OUTPUTS, output).setValue(Recipe.SPEED, speed).setValue(Recipe.HEAT, heat).setValue(Recipe.HEATTOREMOVE, heatToRemove);
		return handler.registerRecipe(builder.init());
	}

	public static boolean addLathe(String recipeName, RecipeItem input, RecipeItem output, int speed, LatheModes mode) {
		IRecipeHandler handler = RecipeRegistry.getRecipeHandler("Lathe");
		IRecipeBuilder builder = handler.getDefaultTemplate();
		builder.setValue(Recipe.INPUTS, new RecipeItem[] { input }).setValue(Recipe.OUTPUTS, new RecipeItem[] { output }).setValue(Recipe.SPEED, speed).setValue(LATHEMODE, mode);
		return handler.registerRecipe(builder.init());
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
		builder.setValue(Recipe.INPUTS, new RecipeItem[] { input }).setValue(Recipe.OUTPUTS, new RecipeItem[] { output }).setValue(Recipe.SPEED, speed).setValue(Recipe.HEAT, heat).setValue(Recipe.HEATTOREMOVE, heatToRemove);
		return handler.registerRecipe(builder.init());
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