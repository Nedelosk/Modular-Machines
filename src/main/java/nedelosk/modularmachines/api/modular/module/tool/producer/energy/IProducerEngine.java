package nedelosk.modularmachines.api.modular.module.tool.producer.energy;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.tool.producer.IProducer;
import nedelosk.modularmachines.api.modular.module.tool.producer.machine.IProducerMachine;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.recipes.IRecipeManager;
import nedelosk.modularmachines.api.recipes.RecipeInput;

public interface IProducerEngine extends IProducer {

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
