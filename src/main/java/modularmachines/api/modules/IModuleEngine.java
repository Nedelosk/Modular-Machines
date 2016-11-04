package modularmachines.api.modules;

import modularmachines.api.modules.energy.IModuleKinetic;
import modularmachines.api.modules.properties.IModuleKineticProperties;

public interface IModuleEngine extends IModuleKinetic, IModuleWorker, ITickable, IModuleKineticProperties {
}
