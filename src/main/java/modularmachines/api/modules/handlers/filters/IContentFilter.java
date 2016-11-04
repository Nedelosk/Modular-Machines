package modularmachines.api.modules.handlers.filters;

import javax.annotation.Nonnull;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.state.IModuleState;

public interface IContentFilter<C, M extends IModule> {

	/**
	 * Test if a item valid for the index.
	 */
	boolean isValid(int index, @Nonnull C content, IModuleState<M> module);
}
