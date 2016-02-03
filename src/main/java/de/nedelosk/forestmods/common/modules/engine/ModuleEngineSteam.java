package de.nedelosk.forestmods.common.modules.engine;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.recipes.IRecipeManager;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modular.recipes.RecipeManagerSteam;

public class ModuleEngineSteam extends ModuleEngine {

	public ModuleEngineSteam(String moduleUID, int speedModifier) {
		super(moduleUID, speedModifier, "Steam");
	}

	@Override
	public int getMaterialModifier(ModuleStack stack) {
		return 2;
	}

	@Override
	public IRecipeManager creatRecipeManager(IModular modular, String recipeName, int materialModifier, RecipeItem[] inputs, Object... craftingModifier) {
		return new RecipeManagerSteam(modular, recipeName, materialModifier, inputs, craftingModifier);
	}

	@Override
	public IRecipeManager creatRecipeManager() {
		return new RecipeManagerSteam();
	}
}
