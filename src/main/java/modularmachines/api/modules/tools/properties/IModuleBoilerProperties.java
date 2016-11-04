package modularmachines.api.modules.tools.properties;

import modularmachines.api.modules.IModuleProperties;
import modularmachines.api.modules.state.IModuleState;

public interface IModuleBoilerProperties extends IModuleProperties {

	int getWaterPerWork(IModuleState state);
}
