package nedelosk.modularmachines.api.modular.module.basic.energy;

import nedelosk.modularmachines.api.modular.module.basic.gui.IModuleGuiWithWidgets;
import nedelosk.modularmachines.api.modular.module.basic.inventory.IModuleInventory;

public interface IModuleBattery extends IModuleGuiWithWidgets, IModuleInventory{

	int getMaxEnergyStored();
	
	int getMaxEnergyReceive();
	
	int getMaxEnergyExtract();
	
	int getSpeedModifier();
	
}
