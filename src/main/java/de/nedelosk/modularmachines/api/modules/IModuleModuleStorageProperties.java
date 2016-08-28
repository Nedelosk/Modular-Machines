package de.nedelosk.modularmachines.api.modules;

import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;

public interface IModuleModuleStorageProperties extends IModuleProperties {

	int getAllowedComplexity(IModuleContainer container);

	boolean isValidForPosition(EnumStoragePosition position, IModuleContainer container);
}
