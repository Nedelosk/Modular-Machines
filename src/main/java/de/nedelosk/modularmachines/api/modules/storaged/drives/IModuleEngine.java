package de.nedelosk.modularmachines.api.modules.storaged.drives;

import de.nedelosk.modularmachines.api.modules.energy.IModuleKinetic;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleEngine extends IModuleDrive, IModuleKinetic, IModuleKineticProperties{

	boolean isWorking(IModuleState state);
}
