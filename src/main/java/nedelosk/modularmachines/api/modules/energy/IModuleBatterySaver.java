package nedelosk.modularmachines.api.modules.energy;

import cofh.api.energy.EnergyStorage;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface IModuleBatterySaver extends IModuleSaver {

	EnergyStorage getStorage(ModuleStack stack);

	int getSpeedModifier();

	void setSpeedModifier(int speedModifier);

	int getBatteryCapacity();

	void setBatteryCapacity(int batteryCapacity);

	int getEnergyModifier();

	void setEnergyModifier(int energyModifier);
}
