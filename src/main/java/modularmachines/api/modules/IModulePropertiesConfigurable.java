package modularmachines.api.modules;

import modularmachines.api.modules.containers.IModuleContainer;
import net.minecraftforge.common.config.Configuration;

public interface IModulePropertiesConfigurable extends IModuleProperties {

	void processConfig(IModuleContainer container, Configuration config);
}
