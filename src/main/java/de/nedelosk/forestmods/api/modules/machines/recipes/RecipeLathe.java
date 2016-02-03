package de.nedelosk.forestmods.api.modules.machines.recipes;

import de.nedelosk.forestmods.api.recipes.IMachineMode;
import de.nedelosk.forestmods.api.recipes.Recipe;
import de.nedelosk.forestmods.api.recipes.RecipeItem;

public final class RecipeLathe extends Recipe {

	public RecipeLathe(RecipeItem input, RecipeItem output0, RecipeItem output1, int speedModifier, int energy, LatheModes mode) {
		super(new RecipeItem[] { input }, new RecipeItem[] { output0, output1 }, speedModifier, energy, "Lathe", mode);
	}

	public RecipeLathe(RecipeItem input, RecipeItem output0, int speedModifier, int energy, LatheModes mode) {
		super(new RecipeItem[] { input }, new RecipeItem[] { output0 }, speedModifier, energy, "Lathe", mode);
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
