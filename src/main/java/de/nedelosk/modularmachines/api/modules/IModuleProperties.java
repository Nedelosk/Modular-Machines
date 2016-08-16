package de.nedelosk.modularmachines.api.modules;

import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;

public interface IModuleProperties {

	int getComplexity(IModuleContainer container);

	/**
	 * The size of the module.
	 */
	EnumModuleSize getSize(IModuleContainer container);

}
