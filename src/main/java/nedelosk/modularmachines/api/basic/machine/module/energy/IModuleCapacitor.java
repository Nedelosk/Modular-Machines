package nedelosk.modularmachines.api.basic.machine.module.energy;

import nedelosk.modularmachines.api.basic.machine.modular.IModular;
import nedelosk.modularmachines.api.basic.machine.module.IModule;

public interface IModuleCapacitor extends IModule {

	int getSpeedModifier();
	
	int getEnergyModifier();
	
	boolean canWork(IModular modular);
	
}
