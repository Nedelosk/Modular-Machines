package de.nedelosk.modularmachines.api.modules;

import java.util.List;

import javax.annotation.Nonnull;

import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleListener {

	/**
	 * Create the ModulePages for the module.
	 */
	@Nonnull
	List<IModulePage> createPages(IModuleState state, IModuleState listenerState);

}
