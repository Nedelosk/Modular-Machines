package de.nedelosk.modularmachines.api.modules.storaged.storage;

import de.nedelosk.modularmachines.api.modules.ModuleProperties;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;

public class ModuleBatteryProperties extends ModuleProperties implements IModuleBatteryProperties {

	protected final int capacity;
	protected final int maxReceive;
	protected final int maxExtract;

	public ModuleBatteryProperties(int complexity, EnumModuleSize size, int capacity, int maxTransfer) {
		this(capacity, size, capacity, maxTransfer, maxTransfer);
	}

	public ModuleBatteryProperties(int complexity, EnumModuleSize size, int capacity, int maxReceive, int maxExtract) {
		super(complexity, size);
		this.capacity = capacity;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
	}

	@Override
	public int getCapacity(IModuleState state) {
		return capacity;
	}

	@Override
	public int getMaxReceive(IModuleState state) {
		return maxReceive;
	}

	@Override
	public int getMaxExtract(IModuleState state) {
		return maxExtract;
	}
}
