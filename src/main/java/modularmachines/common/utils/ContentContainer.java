package modularmachines.common.utils;

import java.util.ArrayList;
import java.util.List;

import modularmachines.api.modules.Module;
import modularmachines.common.inventory.IContentFilter;

public class ContentContainer<C> implements IContentContainer<C> {

	protected final boolean isInput;
	protected final int index;
	protected final List<IContentFilter<C, Module>> filters;
	protected final Module module;
	protected C content;

	public ContentContainer(int index, boolean isInput, Module module) {
		this.index = index;
		this.isInput = isInput;
		this.module = module;
		this.filters = new ArrayList<>();
	}
	
	@Override
	public boolean hasContent(){
		return this.content != null;
	}
	
	@Override
	public void set(C content) {
		this.content = content;
	}
	
	@Override
	public C get() {
		return content;
	}
	
	@Override
	public boolean isInput() {
		return isInput;
	}
	
	@Override
	public int getIndex() {
		return index;
	}
	
	@Override
	public Module getModule() {
		return module;
	}
	
	@Override
	public void markDirty(){
		
	}
	
	@Override
	public IContentContainer<C> addFilter(IContentFilter<C, Module> filter){
		filters.add(filter);
		return this;
	}
	
	@Override
	public List<IContentFilter<C, Module>> getFilters() {
		return filters;
	}
	
	@Override
	public boolean isValid(C content) {
		if (content == null) {
			return false;
		}
		if (filters.isEmpty()) {
			return !isInput;
		}
		for (IContentFilter<C, Module> filter : filters) {
			if (filter.isValid(index, content, module)) {
				return true;
			}
		}
		return false;
	}
}
