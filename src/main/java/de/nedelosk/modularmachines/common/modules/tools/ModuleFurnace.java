package de.nedelosk.modularmachines.common.modules.tools;

import java.util.List;

import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerGroup;
import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerSlot;
import de.nedelosk.modularmachines.api.modules.IModule;
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
	public boolean canAssembleGroup(IAssemblerGroup group) {
		return false;
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
