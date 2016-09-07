package de.nedelosk.modularmachines.api.modules.properties;

import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;

public interface IModuleControllerProperties extends IModuleProperties {

	int getAllowedComplexity(IModuleContainer container);

}
