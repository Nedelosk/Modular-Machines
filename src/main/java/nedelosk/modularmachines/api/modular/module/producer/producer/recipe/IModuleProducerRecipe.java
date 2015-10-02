package nedelosk.modularmachines.api.modular.module.producer.producer.recipe;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.producer.producer.IModuleProducer;
import nedelosk.modularmachines.api.recipes.IRecipeManager;
import nedelosk.modularmachines.api.recipes.RecipeInput;

public interface IModuleProducerRecipe extends IModuleProducer {

	boolean addOutput(IModular modular);
	
	boolean removeInput(IModular modular);
	
	String getRecipeName();
	
	RecipeInput[] getInputs(IModular modular);
	
	IRecipeManager getRecipeManager();
	
}
