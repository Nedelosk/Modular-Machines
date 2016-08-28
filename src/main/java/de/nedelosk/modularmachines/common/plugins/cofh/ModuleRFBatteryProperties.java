package de.nedelosk.modularmachines.common.plugins.cofh;

import cofh.api.energy.IEnergyContainerItem;
import de.nedelosk.modularmachines.api.modules.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storages.ModuleBatteryProperties;

public class ModuleRFBatteryProperties extends ModuleBatteryProperties {

	public final IEnergyContainerItem containerItem;

	public ModuleRFBatteryProperties(int complexity, EnumModuleSize size, int capacity, int maxTransfer, int tier, IEnergyContainerItem containerItem) {
		super(complexity, size, capacity, maxTransfer, tier);
		this.containerItem = containerItem;
	}

	public ModuleRFBatteryProperties(int complexity, EnumModuleSize size, int capacity, int maxReceive, int maxExtract, int tier, IEnergyContainerItem containerItem) {
		super(complexity, size, capacity, maxReceive, maxExtract, tier);
		this.containerItem = containerItem;
	}
}
