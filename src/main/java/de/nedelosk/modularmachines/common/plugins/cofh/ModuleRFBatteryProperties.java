package de.nedelosk.modularmachines.common.plugins.cofh;

import cofh.api.energy.IEnergyContainerItem;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.storage.ModuleBatteryProperties;

public class ModuleRFBatteryProperties extends ModuleBatteryProperties {

	public final IEnergyContainerItem containerItem;

	public ModuleRFBatteryProperties(int complexity, EnumModuleSize size, int capacity, int maxTransfer, IEnergyContainerItem containerItem) {
		super(complexity, size, capacity, maxTransfer);
		this.containerItem = containerItem;
	}

	public ModuleRFBatteryProperties(int complexity, EnumModuleSize size, int capacity, int maxReceive, int maxExtract, IEnergyContainerItem containerItem) {
		super(complexity, size, capacity, maxReceive, maxExtract);
		this.containerItem = containerItem;
	}
}
