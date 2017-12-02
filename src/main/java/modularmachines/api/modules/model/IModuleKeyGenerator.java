package modularmachines.api.modules.model;

import modularmachines.api.modules.IModule;

/**
 * This is used to generate a cache key for the model of a module.
 */
public interface IModuleKeyGenerator {
	String generateKey(IModule module);
}
