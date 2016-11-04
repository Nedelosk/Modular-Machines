package modularmachines.api.modules.tools.properties;

import modularmachines.api.modules.IModuleProperties;
import modularmachines.api.modules.state.IModuleState;

public interface IModuleMachineProperties extends IModuleProperties {

	double getMaxSpeed(IModuleState state);

	int getWorkTimeModifier(IModuleState state);
}
