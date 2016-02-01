package nedelosk.modularmachines.api.modules.engine;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.IModuleAddable;
import nedelosk.modularmachines.api.modules.basic.IModuleUpdatable;
import nedelosk.modularmachines.api.modules.basic.IModuleWithRenderer;
import nedelosk.modularmachines.api.modules.integration.IModuleWaila;
import nedelosk.modularmachines.api.modules.machines.IModuleMachine;
import nedelosk.modularmachines.api.modules.machines.IModuleMachineSaver;
import nedelosk.modularmachines.api.recipes.IRecipeManager;
import nedelosk.modularmachines.api.recipes.RecipeItem;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface IModuleEngine extends IModuleAddable, IModuleUpdatable, IModuleWaila, IModuleWithRenderer {

	int getSpeedModifier(ModuleStack stack);

	int getMaterialModifier(ModuleStack stack);

	String getType();

	IRecipeManager creatRecipeManager(IModular modular, String recipeName, int materialModifier, RecipeItem[] inputs, Object... craftingModifier);

	IRecipeManager creatRecipeManager();

	int getBurnTimeTotal(IModular modular, int speedModifier, ModuleStack<IModuleMachine, IModuleMachineSaver> stackMachine,
			ModuleStack<IModuleEngine, IModuleEngineSaver> stackEngine);
}
