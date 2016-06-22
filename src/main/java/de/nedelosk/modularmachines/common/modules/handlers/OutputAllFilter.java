package de.nedelosk.modularmachines.common.modules.handlers;

import de.nedelosk.modularmachines.api.modules.handlers.IContentFilter;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public class OutputAllFilter implements IContentFilter {

	@Override
	public boolean isValid(int index, Object content, IModuleState module) {
		return true;
	}
}