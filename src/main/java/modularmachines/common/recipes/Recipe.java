package modularmachines.common.recipes;

import modularmachines.api.recipes.IRecipe;
import modularmachines.api.recipes.RecipeItem;

public abstract class Recipe implements IRecipe {

	/*public static final PropertyRecipeItems INPUTS = new PropertyRecipeItems("inputs");
	public static final PropertyRecipeItems OUTPUTS = new PropertyRecipeItems("outputs");
	public static final PropertyString NAME = new PropertyString("name", null);
	public static final PropertyString CATEGORY = new PropertyString("category", null);
	public static final PropertyInteger SPEED = new PropertyInteger("speed", 0);
	public static final PropertyDouble HEAT = new PropertyDouble("heat", 0);
	public static final PropertyDouble HEATTOREMOVE = new PropertyDouble("removeHeat", 0);
	public static final PropertyDouble KINETIC = new PropertyDouble("kinetic", 0);*/
	
	protected final RecipeItem[] inputItems;
	protected final RecipeItem[] outputItems;
	protected final int speed;
	
	public Recipe(RecipeItem[] inputItems, RecipeItem[] outputItems, int speed) {
		this.inputItems = inputItems;
		this.outputItems = outputItems;
		this.speed = speed;
	}
	
	@Override
	public RecipeItem[] getInputItems() {
		return inputItems;
	}
	
	@Override
	public RecipeItem[] getOutputItems() {
		return outputItems;
	}

	@Override
	public int getSpeed() {
		return speed;
	}
}
