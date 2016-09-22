package de.nedelosk.modularmachines.common.plugins.cofh;

import de.nedelosk.modularmachines.api.modules.EnumModuleSizes;
import de.nedelosk.modularmachines.api.modules.storage.energy.ModuleBatteryProperties;

public class ModuleRFBatteryProperties extends ModuleBatteryProperties {

	public ModuleRFBatteryProperties(int complexity, EnumModuleSizes size, int capacity, int maxTransfer, int tier) {
		super(complexity, size, capacity, maxTransfer, tier);
	}

	public ModuleRFBatteryProperties(int complexity, EnumModuleSizes size, int capacity, int maxReceive, int maxExtract, int tier) {
		super(complexity, size, capacity, maxReceive, maxExtract, tier);
	}
}
