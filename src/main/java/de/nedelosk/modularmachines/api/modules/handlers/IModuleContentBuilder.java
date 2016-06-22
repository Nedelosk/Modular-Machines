package de.nedelosk.modularmachines.api.modules.handlers;

import de.nedelosk.modularmachines.api.modules.IModule;

public interface IModuleContentBuilder<C, M extends IModule> extends IModuleBuilder<M> {

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
	
	@Override
	IModuleContentHandler<C, M> build();
}
