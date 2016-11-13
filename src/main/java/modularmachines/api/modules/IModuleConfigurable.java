package modularmachines.api.modules;

import net.minecraftforge.common.config.Configuration;

import modularmachines.api.modules.containers.IModuleContainer;

public interface IModuleConfigurable extends IModule {

	void processConfig(IModuleContainer container, Configuration config);
}
