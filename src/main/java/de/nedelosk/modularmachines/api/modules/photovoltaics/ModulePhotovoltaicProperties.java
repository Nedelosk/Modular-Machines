package de.nedelosk.modularmachines.api.modules.photovoltaics;

import de.nedelosk.modularmachines.api.modules.ModuleProperties;
import de.nedelosk.modularmachines.api.modules.containers.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraftforge.common.config.Configuration;

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
