package modularmachines.api.modules.storage.module;

import javax.annotation.Nonnull;

import modularmachines.api.modules.state.IModuleState;

public interface IModuleHandler extends IModuleStorage {

	@Nonnull
	IModuleState getState();

	@Nonnull
	IModuleStorage getDefaultStorage();
}
