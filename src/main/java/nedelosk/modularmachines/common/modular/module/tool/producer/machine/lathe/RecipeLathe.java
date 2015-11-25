package nedelosk.modularmachines.common.modular.module.tool.producer.machine.lathe;

import nedelosk.modularmachines.api.modular.module.basic.basic.IMachineMode;
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
	
	@Override
	public boolean matches(Object[] craftingModifiers) {
		if(craftingModifiers == null || craftingModifiers.length == 0)
			return false;
		if(craftingModifiers[0] instanceof LatheModes){
			LatheModes mode = (LatheModes) craftingModifiers[0];
			if(mode == this.modifiers[0]){
				return true;
			}
		}
		return false;
	}

}
