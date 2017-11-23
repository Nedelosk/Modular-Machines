package modularmachines.api.modules.components;

import modularmachines.api.components.IComponent;
import modularmachines.api.modules.IModule;

public interface IModuleComponent extends IComponent<IModule> {
	
	/**
	 * Called after all components where added to the module.
	 */
	default void onCreateModule() {
	}
}
