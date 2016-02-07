package de.nedelosk.forestmods.api.modules.engine;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.IModuleAddable;
import de.nedelosk.forestmods.api.modules.basic.IModuleUpdatable;
import de.nedelosk.forestmods.api.modules.basic.IModuleWithRenderer;
import de.nedelosk.forestmods.api.modules.integration.IModuleWaila;
import de.nedelosk.forestmods.api.modules.machines.IModuleMachine;
import de.nedelosk.forestmods.api.modules.machines.IModuleMachineSaver;
import de.nedelosk.forestmods.api.recipes.IRecipeManager;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import de.nedelosk.forestmods.api.utils.ModuleStack;

public interface IModuleEngine extends IModuleAddable, IModuleUpdatable, IModuleWaila, IModuleWithRenderer {

	int getMaterialModifier(ModuleStack stack);

	String getType();

	IRecipeManager creatRecipeManager(IModular modular, String recipeName, int materialModifier, RecipeItem[] inputs, Object... craftingModifier);

	IRecipeManager creatRecipeManager();

	int getBurnTimeTotal(IModular modular, int speedModifier, ModuleStack<IModuleMachine, IModuleMachineSaver> stackMachine,
			ModuleStack<IModuleEngine, IModuleEngineSaver> stackEngine);
}
