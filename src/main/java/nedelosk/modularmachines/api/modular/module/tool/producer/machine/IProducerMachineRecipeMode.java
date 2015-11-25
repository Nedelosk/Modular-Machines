package nedelosk.modularmachines.api.modular.module.tool.producer.machine;

import nedelosk.modularmachines.api.modular.module.basic.basic.IMachineMode;

public interface IProducerMachineRecipeMode extends IProducerMachineRecipe {

	IMachineMode getMode();
	
	void setMode(IMachineMode mode);
	
	Class<? extends IMachineMode> getModeClass();
	
}
