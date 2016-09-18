package de.nedelosk.modularmachines.api.modules.storage.energy;

import de.nedelosk.modularmachines.api.modules.EnumModuleSizes;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.position.EnumModulePositions;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.module.StorageModuleProperties;
import net.minecraftforge.common.config.Configuration;

public class ModuleBatteryProperties extends StorageModuleProperties implements IModuleBatteryProperties {

	protected final int defaultCapacity;
	protected final int defaultMaxReceive;
	protected final int defaultMaxExtract;
	protected final int defaultTier;
	protected int capacity;
	protected int maxReceive;
	protected int maxExtract;
	protected int tier;

	public ModuleBatteryProperties(int complexity, EnumModuleSizes size, int capacity, int maxTransfer, int tier) {
		this(complexity, size, capacity, maxTransfer, maxTransfer, tier);
	}

	public ModuleBatteryProperties(int complexity, EnumModuleSizes size, int capacity, int maxReceive, int maxExtract, int tier) {
		super(complexity, size, EnumModulePositions.SIDE, EnumModulePositions.BACK);
		this.defaultCapacity = capacity;
		this.defaultMaxReceive = maxReceive;
		this.defaultMaxExtract = maxExtract;
		this.defaultTier = tier;
		this.capacity = capacity;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
		this.tier = tier;
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
	public int getTier(IModuleContainer container) {
		return tier;
	}

	@Override
	public void processConfig(IModuleContainer container, Configuration config) {
		super.processConfig(container, config);
		capacity = config.getInt("capacity", "modules." + container.getRegistryName(), defaultCapacity, 1, Integer.MAX_VALUE, "");
		maxExtract = config.getInt("maxExtract", "modules." + container.getRegistryName(), defaultMaxExtract, 1, Integer.MAX_VALUE, "");
		maxReceive = config.getInt("maxReceive", "modules." + container.getRegistryName(), defaultMaxReceive, 1, Integer.MAX_VALUE, "");
		tier = config.getInt("tier", "modules." + container.getRegistryName(), defaultTier, 1, 4, "1 = LV, 2 = MV, 3 = HV, 4 = EV");
	}
}
