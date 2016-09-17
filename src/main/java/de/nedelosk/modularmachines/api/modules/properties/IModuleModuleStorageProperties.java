package de.nedelosk.modularmachines.api.modules.properties;

import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.storage.IStorageModuleProperties;

public interface IModuleModuleStorageProperties extends IStorageModuleProperties {

	int getAllowedComplexity(IModuleContainer container);
}
