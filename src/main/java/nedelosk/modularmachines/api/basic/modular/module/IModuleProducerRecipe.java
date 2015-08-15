package nedelosk.modularmachines.api.basic.modular.module;

import nedelosk.modularmachines.api.basic.modular.IModular;
import nedelosk.modularmachines.api.basic.modular.module.recipes.IRecipeManager;
import nedelosk.modularmachines.api.basic.modular.module.recipes.RecipeInput;

public interface IModuleProducerRecipe extends IModuleProducer {

	boolean addOutput(IModular modular);
	
	boolean removeInput(IModular modular);
	
	String getRecipeName();
	
	RecipeInput[] getInputs(IModular modular);
	
	IRecipeManager getRecipeManager();
	
}
