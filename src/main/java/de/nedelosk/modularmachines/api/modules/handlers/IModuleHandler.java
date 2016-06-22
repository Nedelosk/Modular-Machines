package de.nedelosk.modularmachines.api.modules.handlers;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleHandler {

	/**
	 * @deprecated Us the modular from the module state.
	 */
	@Deprecated
	IModular getModular();

	/**
	 * @return The state of the module of the handler.
	 */
	IModuleState getModuleState();
}
