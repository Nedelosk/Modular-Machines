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
}
