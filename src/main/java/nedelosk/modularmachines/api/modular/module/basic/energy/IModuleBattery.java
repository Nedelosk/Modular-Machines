package nedelosk.modularmachines.api.modular.module.basic.energy;

import nedelosk.modularmachines.api.modular.module.basic.gui.IModuleGui;

public interface IModuleBattery extends IModuleGui{

	int getMaxEnergyStored();
	
	int getMaxEnergyReceive();
	
	int getMaxEnergyExtract();
	
	int getSpeedModifier();
	
}
