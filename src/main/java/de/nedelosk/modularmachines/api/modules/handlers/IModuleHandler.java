package de.nedelosk.modularmachines.api.modules.handlers;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleHandler {

	/**
	 * @return The state of the module of the handler.
	 */
	IModuleState getModuleState();
}
