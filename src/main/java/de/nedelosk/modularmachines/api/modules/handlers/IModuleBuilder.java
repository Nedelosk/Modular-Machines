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
	 * Set the modular of the handler.
	 * @deprecated Us the modular from the module state.
	 */
	@Deprecated
	void setModular(IModular modular);

	/**
	 * Build a handler from the builder.
	 */
	IModuleHandler build();
}
