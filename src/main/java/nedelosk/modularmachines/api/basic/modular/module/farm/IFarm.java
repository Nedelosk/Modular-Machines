package nedelosk.modularmachines.api.basic.modular.module.farm;

import nedelosk.modularmachines.api.basic.modular.IModular;
import nedelosk.modularmachines.api.basic.modular.module.IModule;

public interface IFarm {

	void updateFarm(IModule module, IModular modular);
	
	String getName();
	
}
