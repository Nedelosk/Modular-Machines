package nedelosk.modularmachines.api.basic.machine.module.manager;

import nedelosk.modularmachines.api.basic.machine.module.IModuleGui;

public interface IModuleEnergyManager extends IModuleGui, IModuleManager {

	int getSpeedModifier();
	
}
