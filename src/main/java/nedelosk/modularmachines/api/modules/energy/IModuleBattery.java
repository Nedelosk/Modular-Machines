package nedelosk.modularmachines.api.modules.energy;

import cofh.api.energy.EnergyStorage;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface IModuleBattery extends IModule {

	EnergyStorage getStorage(ModuleStack stack);

	int getSpeedModifier();
}
