package modularmachines.api.modules.properties;

import net.minecraftforge.common.config.Configuration;

import modularmachines.api.modules.ModuleProperties;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.state.IModuleState;

public class ModuleHeaterProperties extends ModuleProperties implements IModuleHeaterProperties {

	protected final double defaultMaxHeat;
	protected final int defaultHeatModifier;
	protected double maxHeat;
	protected int heatModifier;

	public ModuleHeaterProperties(int complexity, double maxHeat, int heatModifier) {
		super(complexity);
		this.defaultMaxHeat = maxHeat;
		this.defaultHeatModifier = heatModifier;
		this.heatModifier = heatModifier;
		this.maxHeat = defaultMaxHeat;
	}

	@Override
	public double getMaxHeat(IModuleState state) {
		return maxHeat;
	}

	@Override
	public int getHeatModifier(IModuleState state) {
		return heatModifier;
	}

	@Override
	public void processConfig(IModuleContainer container, Configuration config) {
		super.processConfig(container, config);
		maxHeat = getDouble(config, "maxHeat", "modules." + container.getItemContainer().getRegistryName(), defaultMaxHeat, 1.0D, 10000.0D, "The max heat of the heater.");
		heatModifier = config.getInt("heatModifier", "modules." + container.getItemContainer().getRegistryName(), defaultHeatModifier, 1, 25, "");
	}
}
