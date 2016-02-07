package de.nedelosk.forestmods.api.modules.storage.battery;

import cofh.api.energy.EnergyStorage;
import de.nedelosk.forestmods.api.modules.IModuleSaver;

public interface IModuleBatterySaver extends IModuleSaver {

	int getSpeedModifier();

	void setSpeedModifier(int speedModifier);

	int getBatteryCapacity();

	void setBatteryCapacity(int batteryCapacity);

	int getEnergyModifier();

	void setEnergyModifier(int energyModifier);

	EnergyStorage getStorage();

	void setStorage(EnergyStorage storage);
}
