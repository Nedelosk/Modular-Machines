package de.nedelosk.modularmachines.api.modules.handlers;

import java.util.List;

import javax.annotation.Nonnull;

public interface IMultiModuleContentHandlerProvider extends IModuleContentHandlerProvider {

	@Nonnull
	List<IModuleContentHandler> getAllContentHandlers();
}
