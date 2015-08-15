package nedelosk.modularmachines.api.basic.modular.module.energy;

import nedelosk.modularmachines.api.basic.modular.IModular;
import nedelosk.modularmachines.api.basic.modular.module.IModule;

public interface IModuleCapacitor extends IModule {

	int getSpeedModifier();
	
	int getEnergyModifier();
	
	boolean canWork(IModular modular);
	
}
