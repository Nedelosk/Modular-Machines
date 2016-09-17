package de.nedelosk.modularmachines.api.modules;

import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;

public interface IModuleProperties {

	int getComplexity(IModuleContainer container);

	/**
	 * The size of the module.
	 */
	EnumModuleSizes getSize(IModuleContainer container);

}
