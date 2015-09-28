package nedelosk.modularmachines.api.basic.machine.module.farm;

import nedelosk.modularmachines.api.basic.machine.modular.IModular;
import nedelosk.modularmachines.api.basic.machine.module.IModule;

public interface IFarm {

	void updateFarm(IModule module, IModular modular);
	
	String getName();
	
}
