package modularmachines.api.modules.storage.module;

import modularmachines.api.modules.EnumModuleSizes;
import modularmachines.api.modules.storage.IStorage;

public interface IDefaultModuleStorage extends IModuleStorage, IStorage {

	/**
	 * @return The size of the storage.
	 */
	EnumModuleSizes getSize();
}
