package nedelosk.modularmachines.api.modular.module.basic.energy;

import nedelosk.modularmachines.api.modular.module.basic.IModule;

public interface IModuleBattery extends IModule{

	int getMaxEnergyStored();
	
	int getMaxEnergyReceive();
	
	int getMaxEnergyExtract();
	
}
