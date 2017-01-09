package modularmachines.common.modules.tools.recipe;

import modularmachines.api.property.PropertyToolMode;
import modularmachines.api.recipes.IRecipe;

public class RecipeHandlerToolMode extends RecipeHandler {

	public final PropertyToolMode propertyMode;

	public RecipeHandlerToolMode(String recipeCategory, PropertyToolMode propertyMode) {
		super(recipeCategory);
		this.propertyMode = propertyMode;
	}

	@Override
	public IRecipe buildDefault() {
		return getDefaultTemplate().setValue(propertyMode, null).init();
	}

	@Override
	public boolean isRecipeValid(IRecipe recipe) {
		if (super.isRecipeValid(recipe)) {
			if (recipe.getValue(propertyMode) == null) {
				return false;
			}
			return true;
		}
		return false;
	}
}
