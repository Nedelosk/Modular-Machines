package nedelosk.modularmachines.api.producers.machines.recipes;

import nedelosk.modularmachines.api.recipes.IMachineMode;
import nedelosk.modularmachines.api.recipes.Recipe;
import nedelosk.modularmachines.api.recipes.RecipeItem;

public class RecipeLathe extends Recipe {

	public RecipeLathe(RecipeItem input, RecipeItem output0, RecipeItem output1, int speedModifier, int energy, LatheModes mode) {
		super(new RecipeItem[] { input }, new RecipeItem[] { output0, output1 }, speedModifier, energy, "Lathe", mode);
	}
	
	public RecipeLathe(RecipeItem input, RecipeItem output0, int speedModifier, int energy, LatheModes mode) {
		super(new RecipeItem[] { input }, new RecipeItem[] { output0 }, speedModifier, energy, "Lathe", mode);
	}
	
	public static enum LatheModes implements IMachineMode{
		ROD("rod");
		
		String name;

		private LatheModes(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
		
	}

}
