package de.nedelosk.modularmachines.api.modules.storage;

import javax.annotation.Nonnull;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleHandler extends IModuleStorage {

	@Nonnull
	IModuleState getState();

	@Nonnull
	IModuleStorage getDefaultStorage();

}
