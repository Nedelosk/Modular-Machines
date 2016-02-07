package de.nedelosk.forestmods.api.modules.storage.battery;

import cofh.api.energy.EnergyStorage;
import de.nedelosk.forestmods.api.modules.IModuleType;

public interface IModuleBatteryType extends IModuleType {

	EnergyStorage getDefaultStorage();
}
