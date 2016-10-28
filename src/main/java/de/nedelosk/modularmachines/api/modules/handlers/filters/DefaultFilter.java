package de.nedelosk.modularmachines.api.modules.handlers.filters;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public class DefaultFilter implements IContentFilter<Object, IModule> {

	public static final IContentFilter INSTANCE = new DefaultFilter();

	private DefaultFilter() {
	}

	@Override
	public boolean isValid(int index, Object content, IModuleState module) {
		return true;
	}
}
