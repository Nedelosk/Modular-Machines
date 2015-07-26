package nedelosk.modularmachines.api.modular.module.energy;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.module.IModule;

public interface IModuleCapacitor extends IModule {

	int getSpeedModifier();
	
	int getEnergyModifier();
	
	boolean canWork(IModular modular);
	
}
