package nedelosk.modularmachines.api.modular.module.tool.producer.machine;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.recipes.IRecipeManager;
import nedelosk.modularmachines.api.recipes.RecipeInput;

public interface IProducerMachineRecipe extends IProducerMachine {

	boolean addOutput(IModular modular, ModuleStack stack);

	boolean removeInput(IModular modular, ModuleStack stack);

	String getRecipeName(ModuleStack stack);

	RecipeInput[] getInputs(IModular modular, ModuleStack stack);

}
