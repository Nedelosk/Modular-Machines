package nedelosk.modularmachines.api.modular.module.producer.producer.recipe;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.producer.producer.IModuleProducer;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.recipes.IRecipeManager;
import nedelosk.modularmachines.api.recipes.RecipeInput;

public interface IModuleProducerRecipe extends IModuleProducer {

	boolean addOutput(IModular modular, ModuleStack stack);
	
	boolean removeInput(IModular modular, ModuleStack stack);
	
	String getRecipeName();
	
	RecipeInput[] getInputs(IModular modular, ModuleStack stack);
	
	IRecipeManager getRecipeManager();
	
}
