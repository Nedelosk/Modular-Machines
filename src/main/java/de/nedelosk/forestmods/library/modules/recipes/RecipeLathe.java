package de.nedelosk.forestmods.library.modules.recipes;

import de.nedelosk.forestmods.library.recipes.IMachineMode;
import de.nedelosk.forestmods.library.recipes.RecipeItem;
import de.nedelosk.forestmods.library.recipes.RecipeWithMode;

public final class RecipeLathe extends RecipeWithMode {

	public RecipeLathe(String recipeName, RecipeItem[] inputs, RecipeItem[] outputs, int speedModifier, int energy, String recipeCategory, Object[] objects) {
		super(recipeName, inputs, outputs, speedModifier, energy, recipeCategory, objects);
	}

	public RecipeLathe(String recipeName, RecipeItem input, RecipeItem output0, RecipeItem output1, int speedModifier, int energy, LatheModes mode) {
		super(recipeName, new RecipeItem[] { input }, new RecipeItem[] { output0, output1 }, speedModifier, energy, "Lathe", mode);
	}

	public RecipeLathe(String recipeName, RecipeItem input, RecipeItem output0, int speedModifier, int energy, LatheModes mode) {
		super(recipeName, new RecipeItem[] { input }, new RecipeItem[] { output0 }, speedModifier, energy, "Lathe", mode);
	}

	public static enum LatheModes implements IMachineMode {
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
