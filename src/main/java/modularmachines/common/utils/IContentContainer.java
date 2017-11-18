package modularmachines.common.utils;

import java.util.List;

import modularmachines.api.modules.Module;
import modularmachines.common.inventory.IContentFilter;

public interface IContentContainer<C> {
	
	boolean hasContent();
	
	void set(C content);
	
	C get();
	
	boolean isInput();
	
	int getIndex();
	
	Module getModule();
	
	void markDirty();
	
	IContentContainer<C> addFilter(IContentFilter<C, Module> filter);
	
	List<IContentFilter<C, Module>> getFilters();
	
	boolean isValid(C content);
	
}
