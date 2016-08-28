package de.nedelosk.modularmachines.api.modules;

import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;

public interface IModuleControllerProperties extends IModuleProperties {

	int getAllowedComplexity(IModuleContainer container);

}
