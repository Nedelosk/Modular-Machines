package modularmachines.common.utils;

import javax.annotation.Nullable;
import java.util.List;

import modularmachines.api.modules.IModule;
import modularmachines.common.inventory.IContentFilter;

public interface IContentContainer<C> {
	
	boolean hasContent();
	
	void set(@Nullable C content);
	
	@Nullable
	C get();
	
	boolean isInput();
	
	int getIndex();
	
	IModule getModule();
	
	void markDirty();
	
	IContentContainer<C> addFilter(IContentFilter<C, IModule> filter);
	
	List<IContentFilter<C, IModule>> getFilters();
	
	boolean isValid(C content);
	
}
