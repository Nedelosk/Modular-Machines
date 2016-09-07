package de.nedelosk.modularmachines.api.modules.properties;

import de.nedelosk.modularmachines.api.modules.EnumStoragePosition;
import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;

public interface IModuleModuleStorageProperties extends IModuleProperties {

	int getAllowedComplexity(IModuleContainer container);

	boolean isValidForPosition(EnumStoragePosition position, IModuleContainer container);
}
