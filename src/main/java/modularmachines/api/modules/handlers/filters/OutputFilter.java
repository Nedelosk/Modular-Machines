package modularmachines.api.modules.handlers.filters;

import modularmachines.api.modules.state.IModuleState;

public class OutputFilter implements IContentFilter {

	public static final OutputFilter INSTANCE = new OutputFilter();

	private OutputFilter() {
	}

	@Override
	public boolean isValid(int index, Object content, IModuleState module) {
		return true;
	}
}