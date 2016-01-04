package nedelosk.modularmachines.api.producers.machines.recipe;

import nedelosk.modularmachines.api.recipes.IMachineMode;

public interface IProducerMachineRecipeMode extends IProducerMachineRecipe {

	IMachineMode getMode();

	void setMode(IMachineMode mode);

	Class<? extends IMachineMode> getModeClass();

}
