/*
 * Copyright (c) 2017 Nedelosk
 *
 * This work (the MOD) is licensed under the "MIT" License, see LICENSE for details.
 */
package modularmachines.api.modules.listeners;

import modularmachines.api.modules.IModule;

public interface IModuleListener {
	
	/**
	 * Called after a module was removed from a {@link modularmachines.api.modules.IModuleHandler}.
	 */
	default void onModuleRemoved(IModule module) {
	}
	
	/**
	 * Called after a module was added to a {@link modularmachines.api.modules.IModuleHandler}.
	 */
	default void onModuleAdded(IModule module) {
	}
	
}
