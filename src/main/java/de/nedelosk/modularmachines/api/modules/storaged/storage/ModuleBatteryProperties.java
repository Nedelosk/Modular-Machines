package de.nedelosk.modularmachines.api.modules.storaged.storage;

import de.nedelosk.modularmachines.api.modules.ModuleProperties;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import net.minecraftforge.common.config.Configuration;

public class ModuleBatteryProperties extends ModuleProperties implements IModuleBatteryProperties {

	protected final int defaultCapacity;
	protected final int defaultMaxReceive;
	protected final int defaultMaxExtract;
	protected int capacity;
	protected int maxReceive;
	protected int maxExtract;

	public ModuleBatteryProperties(int complexity, EnumModuleSize size, int capacity, int maxTransfer) {
		this(complexity, size, capacity, maxTransfer, maxTransfer);
	}

	public ModuleBatteryProperties(int complexity, EnumModuleSize size, int capacity, int maxReceive, int maxExtract) {
		super(complexity, size);
		this.defaultCapacity = capacity;
		this.defaultMaxReceive = maxReceive;
		this.defaultMaxExtract = maxExtract;
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

	@Override
	public void processConfig(IModuleContainer container, Configuration config) {
		super.processConfig(container, config);
		capacity = config.getInt("capacity", "modules." + container.getRegistryName(), defaultCapacity, 1, Integer.MAX_VALUE, "");
		maxExtract = config.getInt("maxExtract", "modules." + container.getRegistryName(), defaultMaxExtract, 1, Integer.MAX_VALUE, "");
		maxReceive = config.getInt("maxReceive", "modules." + container.getRegistryName(), defaultMaxReceive, 1, Integer.MAX_VALUE, "");
	}
}
