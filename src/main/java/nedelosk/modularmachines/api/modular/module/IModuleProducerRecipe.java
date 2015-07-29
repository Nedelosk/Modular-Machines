package nedelosk.modularmachines.api.modular.module;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.module.recipes.IRecipeManager;
import nedelosk.modularmachines.api.modular.module.recipes.RecipeInput;
import nedelosk.modularmachines.common.blocks.tile.TileModularMachine;

public interface IModuleProducerRecipe extends IModuleProducer {

	boolean addOutput(IModular modular);
	
	boolean removeInput(IModular modular);
	
	String getRecipeName();
	
	RecipeInput[] getInputs(IModular modular);
	
	IRecipeManager getRecipeManager();
	
}
