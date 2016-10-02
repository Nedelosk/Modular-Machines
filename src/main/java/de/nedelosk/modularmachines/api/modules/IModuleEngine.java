package de.nedelosk.modularmachines.api.modules;

import de.nedelosk.modularmachines.api.modules.energy.IModuleKinetic;
import de.nedelosk.modularmachines.api.modules.properties.IModuleKineticProperties;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleEngine extends IModuleKinetic, ITickable, IModuleKineticProperties{

	boolean isWorking(IModuleState state);
}
