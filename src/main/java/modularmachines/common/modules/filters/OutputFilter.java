package modularmachines.common.modules.filters;

import modularmachines.api.modules.Module;
import modularmachines.common.inventory.IContentFilter;

public class OutputFilter implements IContentFilter {
	
	public static final OutputFilter INSTANCE = new OutputFilter();
	
	private OutputFilter() {
	}
	
	@Override
	public boolean isValid(int index, Object content, Module module) {
		return true;
	}
}