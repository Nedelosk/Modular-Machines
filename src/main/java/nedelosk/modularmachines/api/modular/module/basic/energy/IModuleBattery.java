package nedelosk.modularmachines.api.modular.module.basic.energy;

import cofh.api.energy.EnergyStorage;
import nedelosk.modularmachines.api.modular.module.basic.gui.IModuleGuiWithWidgets;
import nedelosk.modularmachines.api.modular.module.basic.inventory.IModuleInventory;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;

public interface IModuleBattery extends IModuleGuiWithWidgets, IModuleInventory{
	
	EnergyStorage getStorage(ModuleStack stack);
	
	int getSpeedModifier();
	
}
