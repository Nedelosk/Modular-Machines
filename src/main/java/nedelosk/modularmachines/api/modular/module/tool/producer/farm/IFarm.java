package nedelosk.modularmachines.api.modular.module.tool.producer.farm;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;

public interface IFarm {

	void updateFarm(ModuleStack stack, IModular modular);
	
	String getName();
	
}
