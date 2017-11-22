package modularmachines.common.modules.filters;

import modularmachines.api.modules.IModule;
import modularmachines.common.inventory.IContentFilter;

public class DefaultFilter implements IContentFilter {
	
	public static final IContentFilter INSTANCE = new DefaultFilter();
	
	private DefaultFilter() {
	}
	
	@Override
	public boolean isValid(int index, Object content, IModule module) {
		return true;
	}
}
