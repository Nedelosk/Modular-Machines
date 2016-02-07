package de.nedelosk.forestmods.common.modules.engine;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.recipes.IRecipeManager;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modular.recipes.RecipeManagerEnergy;

public class ModuleEngineEnergy extends ModuleEngine {

	public ModuleEngineEnergy(String moduleUID) {
		super(moduleUID, "Energy");
	}

	@Override
	public int getMaterialModifier(ModuleStack stack) {
		return 1;
	}

	@Override
	public IRecipeManager creatRecipeManager(IModular modular, String recipeName, int materialModifier, RecipeItem[] inputs, Object... craftingModifier) {
		return new RecipeManagerEnergy(modular, recipeName, materialModifier, inputs, craftingModifier);
	}

	@Override
	public IRecipeManager creatRecipeManager() {
		return new RecipeManagerEnergy();
	}
}
