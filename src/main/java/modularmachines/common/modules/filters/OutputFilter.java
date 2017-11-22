package modularmachines.common.modules.filters;

import modularmachines.common.inventory.IContentFilter;
import modularmachines.common.modules.Module;

public class OutputFilter implements IContentFilter {
	
	public static final OutputFilter INSTANCE = new OutputFilter();
	
	private OutputFilter() {
	}
	
	@Override
	public boolean isValid(int index, Object content, Module module) {
		return true;
	}
}