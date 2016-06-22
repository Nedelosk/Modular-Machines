package de.nedelosk.modularmachines.api.modules.handlers;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IContentFilter<C, M extends IModule> {

	boolean isValid(int index, C content, IModuleState<M> module);
}
