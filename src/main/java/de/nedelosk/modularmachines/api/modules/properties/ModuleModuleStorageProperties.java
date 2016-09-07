package de.nedelosk.modularmachines.api.modules.properties;

import de.nedelosk.modularmachines.api.modules.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.EnumStoragePosition;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.ModuleProperties;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import net.minecraftforge.common.config.Configuration;

public class ModuleModuleStorageProperties extends ModuleProperties implements IModuleModuleStorageProperties {

	private final int defaultAllowedComplexity;
	private int allowedComplexity;

	public ModuleModuleStorageProperties(int complexity, EnumModuleSize size, int allowedComplexity) {
		super(complexity, size);
		this.defaultAllowedComplexity = allowedComplexity;
		this.allowedComplexity = allowedComplexity;
	}

	@Override
	public int getAllowedComplexity(IModuleContainer container) {
		return allowedComplexity;
	}

	@Override
	public boolean isValidForPosition(EnumStoragePosition position, IModuleContainer container) {
		IModule module = container.getModule();
		return module instanceof IModuleCasing ? position == EnumStoragePosition.INTERNAL : (module.getSize(container) == EnumModuleSize.LARGE) ? position == EnumStoragePosition.LEFT || position == EnumStoragePosition.RIGHT : position == EnumStoragePosition.TOP || position == EnumStoragePosition.BACK;
	}

	@Override
	public void processConfig(IModuleContainer container, Configuration config) {
		super.processConfig(container, config);
		allowedComplexity = config.getInt("allowedComplexity", "modules." + container.getRegistryName(), defaultAllowedComplexity, 8, 256, "The allowed complexity of the controller.");
	}
}
