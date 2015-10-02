package nedelosk.modularmachines.api.modular.module.basic.energy;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.basic.IModule;

public interface IModuleCapacitor extends IModule {

	int getSpeedModifier();
	
	int getEnergyModifier();
	
	boolean canWork(IModular modular);
	
}
