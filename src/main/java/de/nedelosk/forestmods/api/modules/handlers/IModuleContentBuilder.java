package de.nedelosk.forestmods.api.modules.handlers;

public interface IModuleContentBuilder<C> extends IModuleBuilder {

	void addInsertFilter(int index, IContentFilter<C> filter);

	void addExtractFilter(int index, IContentFilter<C> filter);

	@Override
	IModuleContentHandler<C> build();
}
