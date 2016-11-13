package modularmachines.api.modules;

import net.minecraftforge.common.config.Configuration;

import modularmachines.api.modules.containers.IModuleContainer;

public interface IModulePropertiesConfigurable extends IModuleProperties {

	void processConfig(IModuleContainer container, Configuration config);
}
