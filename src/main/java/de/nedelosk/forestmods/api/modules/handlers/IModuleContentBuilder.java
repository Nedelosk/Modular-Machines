package de.nedelosk.forestmods.api.modules.handlers;

public interface IModuleContentBuilder<C> extends IModuleBuilder {

	void addInsertFilter(int index, IContentFilter<C>... filters);

	void addExtractFilter(int index, IContentFilter<C>... filters);

	@Override
	IModuleContentHandler<C> build();
}
