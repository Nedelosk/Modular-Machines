package modularmachines.api.modules.handlers;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.handlers.filters.IContentFilter;
import modularmachines.api.modules.state.IModuleState;

public interface IModuleContentHandlerBuilder<C, M extends IModule> {

	/**
	 * Add new insert filters.
	 */
	void addInsertFilter(int index, IContentFilter<C, M>... filters);

	/**
	 * Add new extract filters.
	 */
	void addExtractFilter(int index, IContentFilter<C, M>... filters);

	/**
	 * @return True if the builder has no content in it.
	 */
	boolean isEmpty();

	/**
	 * Set the state of the module of the handler.
	 */
	void setModuleState(IModuleState<M> module);

	/**
	 * Build a handler from the builder.
	 */
	IAdvancedModuleContentHandler<C, M> build();
}
