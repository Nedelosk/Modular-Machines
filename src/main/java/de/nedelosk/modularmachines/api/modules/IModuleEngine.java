package de.nedelosk.modularmachines.api.modules;

import de.nedelosk.modularmachines.api.modules.energy.IModuleKinetic;
import de.nedelosk.modularmachines.api.modules.properties.IModuleKineticProperties;

public interface IModuleEngine extends IModuleKinetic, IModuleWorker, ITickable, IModuleKineticProperties {
}
