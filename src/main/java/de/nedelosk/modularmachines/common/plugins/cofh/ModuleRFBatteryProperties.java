package de.nedelosk.modularmachines.common.plugins.cofh;

import de.nedelosk.modularmachines.api.modules.storage.energy.ModuleBatteryProperties;

public class ModuleRFBatteryProperties extends ModuleBatteryProperties {

	public ModuleRFBatteryProperties(int complexity, int capacity, int maxTransfer, int tier) {
		super(complexity, capacity, maxTransfer, tier);
	}

	public ModuleRFBatteryProperties(int complexity, int capacity, int maxReceive, int maxExtract, int tier) {
		super(complexity, capacity, maxReceive, maxExtract, tier);
	}
}
