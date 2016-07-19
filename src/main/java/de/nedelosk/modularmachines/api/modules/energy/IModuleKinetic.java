package de.nedelosk.modularmachines.api.modules.energy;

import de.nedelosk.modularmachines.api.energy.IKineticSource;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleKinetic extends IModule {

	IKineticSource getKineticSource(IModuleState state);
}
