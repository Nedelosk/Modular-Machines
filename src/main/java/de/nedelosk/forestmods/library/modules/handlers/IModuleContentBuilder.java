package de.nedelosk.forestmods.library.modules.handlers;

import de.nedelosk.forestmods.library.modules.IModule;

public interface IModuleContentBuilder<C, M extends IModule> extends IModuleBuilder<M> {

	void addInsertFilter(int index, IContentFilter<C, M>... filters);

	void addExtractFilter(int index, IContentFilter<C, M>... filters);

	@Override
	IModuleContentHandler<C, M> build();
}
