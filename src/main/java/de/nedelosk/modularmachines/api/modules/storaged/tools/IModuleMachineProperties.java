package de.nedelosk.modularmachines.api.modules.storaged.tools;

import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleMachineProperties extends IModuleProperties {

	float getMaxSpeed(IModuleState state);

	int getWorkTimeModifier(IModuleState state);
}
