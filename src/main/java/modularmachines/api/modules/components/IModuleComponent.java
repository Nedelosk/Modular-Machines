package modularmachines.api.modules.components;

import modularmachines.api.components.IComponent;
import modularmachines.api.modules.IModule;

/**
 * Module components are used to give the module a function, an inventory, a bounding box, a fluid handler and much more.
 */
public interface IModuleComponent extends IComponent<IModule> {
	
	/**
	 * Called after all components where added to the module.
	 */
	default void onCreateModule() {
	}
	
	/**
	 * Called after the module was added to the {@link modularmachines.api.modules.IModuleHandler}.
	 */
	default void onModuleAdded() {
	}
	
	/**
	 * Called after the module was loaded from the NBT-Data and added to the {@link modularmachines.api.modules.IModuleHandler}.
	 */
	default void onModuleLoaded() {
	}
	
	/**
	 * Called after the module was removed from the {@link modularmachines.api.modules.IModuleHandler}.
	 */
	default void onModuleRemoved() {
	}
}
