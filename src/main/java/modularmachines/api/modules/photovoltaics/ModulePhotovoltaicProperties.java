package modularmachines.api.modules.photovoltaics;

import net.minecraftforge.common.config.Configuration;

import modularmachines.api.modules.ModuleProperties;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.state.IModuleState;

public class ModulePhotovoltaicProperties extends ModuleProperties implements IModulePhotovoltaicProperties {

	protected final int defaultEnergyModifier;
	protected int energyModifier;

	public ModulePhotovoltaicProperties(int complexity, int energyModifier) {
		super(complexity);
		this.energyModifier = energyModifier;
		this.defaultEnergyModifier = energyModifier;
	}

	@Override
	public int getEnergyModifier(IModuleState state) {
		return energyModifier;
	}

	@Override
	public void processConfig(IModuleContainer container, Configuration config) {
		super.processConfig(container, config);
		energyModifier = config.getInt("energyModifier", "modules." + container.getItemContainer().getRegistryName(), defaultEnergyModifier, 0, 128, "The energy modifier for the photovoltaic");
	}
}
