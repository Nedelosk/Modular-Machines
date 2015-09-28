package nedelosk.modularmachines.api.basic.machine.module;

import nedelosk.modularmachines.api.basic.machine.modular.IModular;
import nedelosk.modularmachines.api.basic.machine.module.recipes.IRecipeManager;
import nedelosk.modularmachines.api.basic.machine.module.recipes.RecipeInput;

public interface IModuleProducerRecipe extends IModuleProducer {

	boolean addOutput(IModular modular);
	
	boolean removeInput(IModular modular);
	
	String getRecipeName();
	
	RecipeInput[] getInputs(IModular modular);
	
	IRecipeManager getRecipeManager();
	
}
