package modularmachines.api.modules.storage.module;

import modularmachines.api.modules.containers.IModuleProvider;

public interface IAddableModuleStorage extends IModuleStorage {

	/**
	 * Add a module state to the storage and set the index of the module state.
	 */
	boolean addModule(IModuleProvider provider);
}
