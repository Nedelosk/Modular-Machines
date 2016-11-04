package modularmachines.api.modules.energy;

import modularmachines.api.energy.IKineticSource;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.state.IModuleState;

public interface IModuleKinetic extends IModule {

	IKineticSource getKineticSource(IModuleState state);
}
