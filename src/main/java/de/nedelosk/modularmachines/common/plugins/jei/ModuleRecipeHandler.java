package de.nedelosk.modularmachines.common.plugins.jei;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class ModuleRecipeHandler implements IRecipeHandler<ModuleRecipeWrapper> {

	protected String categoryUid;
	
	public ModuleRecipeHandler(String categoryUid) {
		this.categoryUid = categoryUid;
	}
	
	@Override
	public Class<ModuleRecipeWrapper> getRecipeClass() {
		return ModuleRecipeWrapper.class;
	}

	@Override
	public String getRecipeCategoryUid() {
		return categoryUid;
	}

	@Override
	public String getRecipeCategoryUid(ModuleRecipeWrapper recipe) {
		return categoryUid;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(ModuleRecipeWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(ModuleRecipeWrapper recipe) {
		if(recipe.getInputs().isEmpty()){
			if(recipe.getFluidInputs().isEmpty()){
				return false;
			}
		}
		if(recipe.getOutputs().isEmpty()){
			if(recipe.getFluidOutputs().isEmpty()){
				return false;
			}
		}
		return true;
	}

}
