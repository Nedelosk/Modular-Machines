package nedelosk.modularmachines.api.modules.engine;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.basic.IModuleWithRenderer;
import nedelosk.modularmachines.api.modules.integration.IModuleWaila;
import nedelosk.modularmachines.api.modules.machines.IModuleMachine;
import nedelosk.modularmachines.api.recipes.IRecipeManager;
import nedelosk.modularmachines.api.recipes.RecipeItem;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface IModuleEngine<S extends IModuleEngineSaver> extends IModuleWaila<S>, IModuleWithRenderer<S> {

	int getSpeedModifier(ModuleStack stack);

	int getMaterialModifier(ModuleStack stack);

	String getType();

	IRecipeManager creatRecipeManager(IModular modular, String recipeName, int materialModifier, RecipeItem[] inputs, Object... craftingModifier);

	IRecipeManager creatRecipeManager();

	int getBurnTimeTotal(IModular modular, int speedModifier, ModuleStack<IModuleMachine> stackMachine, ModuleStack<IModuleEngine> stackEngine);

	int getBurnTimeTotal(IModular modular, ModuleStack<IModuleMachine> stackMachine, ModuleStack<IModuleEngine> stackEngine);
}
