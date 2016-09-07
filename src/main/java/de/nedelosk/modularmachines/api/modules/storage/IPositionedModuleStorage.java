package de.nedelosk.modularmachines.api.modules.storage;

import de.nedelosk.modularmachines.api.modules.EnumStoragePosition;

public interface IPositionedModuleStorage extends IAddableModuleStorage, IAdvancedModuleStorage{

	EnumStoragePosition getPosition();

}
