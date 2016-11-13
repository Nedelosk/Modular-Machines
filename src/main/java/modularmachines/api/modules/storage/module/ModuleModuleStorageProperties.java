package modularmachines.api.modules.storage.module;

import net.minecraftforge.common.config.Configuration;

import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.position.IModulePostion;
import modularmachines.api.modules.properties.IModuleModuleStorageProperties;

public class ModuleModuleStorageProperties extends StorageModuleProperties implements IModuleModuleStorageProperties {

	protected final int defaultAllowedComplexity;
	protected int allowedComplexity;

	public ModuleModuleStorageProperties(int complexity, int allowedComplexity, IModulePostion... position) {
		super(complexity, position);
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
		allowedComplexity = config.getInt("allowedComplexity", "modules." + container.getItemContainer().getRegistryName(), defaultAllowedComplexity, 2, 256, "The allowed complexity of the controller.");
	}
}
