package de.nedelosk.modularmachines.api.modules.handlers;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IContentFilter<C, M extends IModule> {

	/**
	 * Test if a item valid for the index.
	 */
	boolean isValid(int index, C content, IModuleState<M> module);
}
