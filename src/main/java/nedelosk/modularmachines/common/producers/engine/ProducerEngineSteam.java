package nedelosk.modularmachines.common.producers.engine;

import crazypants.enderio.machine.recipe.RecipeInput;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.engine.ModuleEngine;
import nedelosk.modularmachines.api.recipes.IRecipeManager;
import nedelosk.modularmachines.api.utils.ModuleStack;
import nedelosk.modularmachines.common.modular.utils.RecipeManagerSteam;

public class ProducerEngineSteam extends ModuleEngine {

	public ProducerEngineSteam(String modifier, int speedModifier) {
		super(modifier, speedModifier, "Steam");
	}

	@Override
	public int getMaterialModifier(ModuleStack stack) {
		return 2;
	}

	@Override
	public IRecipeManager creatRecipeManager(IModular modular, String recipeName, int materialModifier, RecipeInput[] inputs, Object... craftingModifier) {
		return new RecipeManagerSteam(modular, recipeName, materialModifier, inputs, craftingModifier);
	}

	@Override
	public IRecipeManager creatRecipeManager() {
		return new RecipeManagerSteam();
	}
}
