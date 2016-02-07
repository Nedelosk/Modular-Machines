package de.nedelosk.forestmods.common.modules.storage;

import cofh.api.energy.EnergyStorage;
import de.nedelosk.forestmods.api.modules.storage.battery.IModuleBatteryType;

public class ModuleBatteryType implements IModuleBatteryType {

	private final EnergyStorage defaultStorage;

	public ModuleBatteryType(EnergyStorage defaultStorage) {
		this.defaultStorage = defaultStorage;
	}

	@Override
	public EnergyStorage getDefaultStorage() {
		return defaultStorage;
	}
}
