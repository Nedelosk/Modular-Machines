package de.nedelosk.forestmods.api.producers.handlers;

public interface IModuleContentBuilder<C> extends IModuleBuilder {

	void addInsertFilter(int index, IContentFilter<C> filter);

	void addExtractFilter(int index, IContentFilter<C> filter);

	void setDisabled(boolean isDisabled);

	@Override
	IModuleContentHandler<C> build();
}
