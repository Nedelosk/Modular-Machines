package de.nedelosk.modularmachines.api.modules.storage.module;

import de.nedelosk.modularmachines.api.modules.EnumModuleSizes;
import de.nedelosk.modularmachines.api.modules.storage.IStorage;

public interface IDefaultModuleStorage extends IModuleStorage, IStorage {

	/**
	 * @return The size of the storage.
	 */
	EnumModuleSizes getSize();

}
