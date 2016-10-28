package de.nedelosk.modularmachines.common.plugins.jei;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class ModuleRecipeHandler<R extends ModuleRecipeWrapper> implements IRecipeHandler<R> {

	public String recipeCategoryUid;
	public Class<R> classRecipe;

	public ModuleRecipeHandler(String recipeCategoryUid, Class<R> classRecipe) {
		this.recipeCategoryUid = recipeCategoryUid;
		this.classRecipe = classRecipe;
	}

	@Override
	public Class<R> getRecipeClass() {
		return classRecipe;
	}

	@Override
	public String getRecipeCategoryUid() {
		return recipeCategoryUid;
	}

	@Override
	public String getRecipeCategoryUid(R recipe) {
		return recipeCategoryUid;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(R recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(R recipe) {
		if (recipe.getInputs().isEmpty()) {
			if (recipe.getFluidInputs().isEmpty()) {
				return false;
			}
		}
		if (recipe.getOutputs().isEmpty()) {
			if (recipe.getFluidOutputs().isEmpty()) {
				return false;
			}
		}
		return true;
	}
}
