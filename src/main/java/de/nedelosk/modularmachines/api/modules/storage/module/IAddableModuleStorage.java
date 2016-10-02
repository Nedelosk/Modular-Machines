package de.nedelosk.modularmachines.api.modules.storage.module;

import de.nedelosk.modularmachines.api.modules.containers.IModuleProvider;

public interface IAddableModuleStorage extends IModuleStorage {

	/**
	 * Add a module state to the storage and set the index of the module state.
	 */
	boolean addModule(IModuleProvider provider);
}
