package nedelosk.modularmachines.api.producers.engine;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.producers.integration.IProducerWaila;
import nedelosk.modularmachines.api.producers.machines.IProducerMachine;
import nedelosk.modularmachines.api.recipes.IRecipeManager;
import nedelosk.modularmachines.api.recipes.RecipeInput;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface IProducerEngine extends IProducerWaila {

	int getSpeedModifier(ModuleStack stack);
	
	int getMaterialModifier(ModuleStack stack);

	String getType();
	
	IRecipeManager getManager(ModuleStack stack);
	
	void setManager(IRecipeManager manager);

	float getProgress();

	void setProgress(float progress);
	
	int getBurnTime(ModuleStack stack);
	
	void setBurnTime(int burnTime);
	
	int getBurnTimeTotal(ModuleStack stack);
	
	void setBurnTimeTotal(int burnTimeTotal);
	
	boolean isWorking();
	
	void setIsWorking(boolean isWorking);
	
	IRecipeManager creatRecipeManager(IModular modular, String recipeName, int materialModifier, RecipeInput[] inputs, Object... craftingModifier);
	
	IRecipeManager creatRecipeManager();
	
	int getBurnTimeTotal(IModular modular, int speedModifier, ModuleStack<IModule, IProducerMachine> stackMachine, ModuleStack<IModule, IProducerEngine> stackEngine);
	
	int getBurnTimeTotal(IModular modular, ModuleStack<IModule, IProducerMachine> stackMachine, ModuleStack<IModule, IProducerEngine> stackEngine);

}
