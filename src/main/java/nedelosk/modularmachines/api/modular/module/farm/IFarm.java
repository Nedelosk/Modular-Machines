package nedelosk.modularmachines.api.modular.module.farm;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.module.IModule;

public interface IFarm {

	void updateFarm(IModule module, IModular modular);
	
	String getName();
	
}
