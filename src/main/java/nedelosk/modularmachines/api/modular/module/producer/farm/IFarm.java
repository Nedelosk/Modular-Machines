package nedelosk.modularmachines.api.modular.module.producer.farm;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.basic.IModule;

public interface IFarm {

	void updateFarm(IModule module, IModular modular);
	
	String getName();
	
}
