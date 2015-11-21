package nedelosk.modularmachines.common.modular.module.tool.producer.machine.lathe;

import nedelosk.modularmachines.api.modular.module.basic.basic.IMachineMode;
import nedelosk.modularmachines.api.recipes.Recipe;
import nedelosk.modularmachines.api.recipes.RecipeItem;

public class RecipeLathe extends Recipe {

	public RecipeLathe(RecipeItem input1, RecipeItem input2, RecipeItem[] output, int speedModifier,
			int energy, LatheModes mode) {
		super(new RecipeItem[] { input1, input2 }, output, speedModifier, energy, "Lathe", mode);
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
