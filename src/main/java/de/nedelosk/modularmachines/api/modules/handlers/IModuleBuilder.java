package de.nedelosk.modularmachines.api.modules.handlers;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleBuilder<M extends IModule> {

	/**
	 * Set the state of the module of the handler.
	 */
	void setModuleState(IModuleState<M> module);

	/**
	 * Build a handler from the builder.
	 */
	IModuleHandler build();
}
