package de.nedelosk.modularmachines.common.modules.tools;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import de.nedelosk.modularmachines.common.modules.ModuleTool;

public class ModuleFurnace extends ModuleTool {

	public ModuleFurnace(int speedModifier, int size) {
		super(speedModifier, size);
	}

	@Override
	public String getRecipeCategory(IModuleState state) {
		return null;
	}

	@Override
	public RecipeItem[] getInputs(IModuleState state) {
		return null;
	}

	@Override
	public boolean canWork(IModuleState state) {
		return false;
	}

}
