package modularmachines.api.modules.handlers;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IMultiModuleContentHandlerProvider extends IModuleContentHandlerProvider {

	@Nonnull
	List<IModuleContentHandler> getAllContentHandlers();

	@Nullable
	<H> H getContentHandlerFromAll(Class<? extends H> handlerClass);
}
