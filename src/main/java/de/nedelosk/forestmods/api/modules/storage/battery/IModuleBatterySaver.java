package de.nedelosk.forestmods.api.modules.storage.battery;

import cofh.api.energy.EnergyStorage;
import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.utils.ModuleStack;

public interface IModuleBatterySaver extends IModuleSaver {

	EnergyStorage getStorage(ModuleStack stack);

	int getSpeedModifier();

	void setSpeedModifier(int speedModifier);

	int getBatteryCapacity();

	void setBatteryCapacity(int batteryCapacity);

	int getEnergyModifier();

	void setEnergyModifier(int energyModifier);
}
