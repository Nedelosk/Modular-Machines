package modularmachines.api.modules;

import modularmachines.api.modules.energy.IModuleKinetic;
import modularmachines.api.modules.properties.IModuleKineticProperties;
import modularmachines.api.modules.state.IModuleState;

public interface IModuleTurbine extends IModuleKinetic, ITickable, IModuleKineticProperties {

	boolean isWorking(IModuleState state);
}
