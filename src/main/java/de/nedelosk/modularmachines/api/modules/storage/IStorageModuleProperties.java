package de.nedelosk.modularmachines.api.modules.storage;

import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;

public interface IStorageModuleProperties extends IModuleProperties {	

	boolean isValidForPosition(IStoragePosition position, IModuleContainer container);
}
