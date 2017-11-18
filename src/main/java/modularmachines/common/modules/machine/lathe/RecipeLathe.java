package modularmachines.common.modules.machine.lathe;

import modularmachines.api.recipes.RecipeItem;
import modularmachines.common.modules.machine.MachineCategorys;
import modularmachines.common.recipes.Recipe;

public class RecipeLathe extends Recipe {
	
	protected final LatheMode mode;
	
	public RecipeLathe(RecipeItem[] inputItems, RecipeItem[] outputItems, int speed, LatheMode mode) {
		super(inputItems, outputItems, speed);
		this.mode = mode;
	}
	
	public LatheMode getMode() {
		return mode;
	}
	
	@Override
	public String getRecipeCategory() {
		return MachineCategorys.LATHE;
	}
	
}
