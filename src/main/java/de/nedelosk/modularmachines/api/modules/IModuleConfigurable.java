package de.nedelosk.modularmachines.api.modules;

import de.nedelosk.modularmachines.api.modules.containers.IModuleContainer;
import net.minecraftforge.common.config.Configuration;

public interface IModuleConfigurable extends IModule {

	void processConfig(IModuleContainer container, Configuration config);

}
