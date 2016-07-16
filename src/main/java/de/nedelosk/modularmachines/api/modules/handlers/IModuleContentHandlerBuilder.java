package de.nedelosk.modularmachines.api.modules.handlers;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

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
	IModuleContentHandlerAdvanced<C, M> build();
}
