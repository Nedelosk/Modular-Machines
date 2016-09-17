package de.nedelosk.modularmachines.api.modules.storage.module;

import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;

public interface IPositionedModuleStorage extends IAddableModuleStorage, IBasicModuleStorage{

	IStoragePosition getPosition();

}
