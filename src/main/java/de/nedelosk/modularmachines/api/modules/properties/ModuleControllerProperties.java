package de.nedelosk.modularmachines.api.modules.properties;

import de.nedelosk.modularmachines.api.modules.ModuleProperties;
import de.nedelosk.modularmachines.api.modules.containers.IModuleContainer;
import net.minecraftforge.common.config.Configuration;

public class ModuleControllerProperties extends ModuleProperties implements IModuleControllerProperties {

	private final int defaultAllowedComplexity;
	private int allowedComplexity;

	public ModuleControllerProperties(int complexity, int allowedComplexity) {
		super(complexity);
		this.defaultAllowedComplexity = allowedComplexity;
		this.allowedComplexity = allowedComplexity;
	}

	@Override
	public int getAllowedComplexity(IModuleContainer container) {
		return allowedComplexity;
	}

	@Override
	public void processConfig(IModuleContainer container, Configuration config) {
		super.processConfig(container, config);
		allowedComplexity = config.getInt("allowedComplexity", "modules." + container.getItemContainer().getRegistryName(), defaultAllowedComplexity, 8, 256, "The allowed complexity of the controller.");
	}
}
