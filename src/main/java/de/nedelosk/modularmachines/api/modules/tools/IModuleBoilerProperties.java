package de.nedelosk.modularmachines.api.modules.tools;

import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleBoilerProperties extends IModuleProperties {

	int getWaterPerWork(IModuleState state);
}
