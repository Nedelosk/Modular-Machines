package modularmachines.common.modules.filters;

import modularmachines.api.modules.IModule;
import modularmachines.common.inventory.IContentFilter;

public class OutputFilter implements IContentFilter {
	
	public static final OutputFilter INSTANCE = new OutputFilter();
	
	private OutputFilter() {
	}
	
	@Override
	public boolean isValid(int index, Object content, IModule module) {
		return true;
	}
}