package de.nedelosk.modularmachines.api.modules.storaged.drives.heaters;

import de.nedelosk.modularmachines.api.modules.ModuleProperties;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import net.minecraftforge.common.config.Configuration;

public class ModuleHeaterProperties extends ModuleProperties implements IModuleHeaterProperties {

	protected final double defaultMaxHeat;
	protected final int defaultHeatModifier;
	protected double maxHeat;
	protected int heatModifier;

	public ModuleHeaterProperties(int complexity, EnumModuleSize size, double maxHeat, int heatModifier) {
		super(complexity, size);
		this.defaultMaxHeat = maxHeat;
		this.defaultHeatModifier = heatModifier;
		this.heatModifier = heatModifier;
		this.maxHeat = defaultMaxHeat;
	}

	@Override
	public double getMaxHeat(IModuleState state){
		return maxHeat;
	}

	@Override
	public int getHeatModifier(IModuleState state){
		return heatModifier;
	}

	@Override
	public void processConfig(IModuleContainer container, Configuration config) {
		super.processConfig(container, config);
		maxHeat = getDouble(config, "maxHeat", "modules." + container.getRegistryName(), defaultMaxHeat, 0.0D, 10000.0D, "");
		heatModifier = config.getInt("heatModifier", "modules." + container.getRegistryName(), defaultHeatModifier, 0, 25, "");
	}
}
