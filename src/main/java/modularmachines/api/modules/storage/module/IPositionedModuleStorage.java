package modularmachines.api.modules.storage.module;

import modularmachines.api.modules.position.IStoragePosition;

public interface IPositionedModuleStorage extends IAddableModuleStorage, IBasicModuleStorage {

	IStoragePosition getPosition();
}
