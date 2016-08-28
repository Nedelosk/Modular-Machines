package de.nedelosk.modularmachines.common.modules.tools.recipe;

import de.nedelosk.modularmachines.api.property.PropertyToolMode;
import de.nedelosk.modularmachines.api.recipes.IRecipe;

public class RecipeHandlerToolMode extends RecipeHandler {

	public final PropertyToolMode propertyMode;

	public RecipeHandlerToolMode(String recipeCategory, PropertyToolMode propertyMode) {
		super(recipeCategory);

		this.propertyMode = propertyMode;
	}

	@Override
	public IRecipe buildDefault() {
		return getDefaultTemplate().set(propertyMode, null).build();
	}

	@Override
	public boolean isRecipeValid(IRecipe recipe) {
		if(super.isRecipeValid(recipe)){
			if(recipe.get(propertyMode) == null){
				return false;
			}
			return true;
		}
		return false;
	}
}
