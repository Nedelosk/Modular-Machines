package nedelosk.modularmachines.common.producers.engine;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.engine.ModuleEngine;
import nedelosk.modularmachines.api.recipes.IRecipeManager;
import nedelosk.modularmachines.api.recipes.RecipeItem;
import nedelosk.modularmachines.api.utils.ModuleStack;
import nedelosk.modularmachines.common.modular.utils.RecipeManagerSteam;

public class ProducerEngineSteam extends ModuleEngine {

	public ProducerEngineSteam(String moduleUID, int speedModifier) {
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
