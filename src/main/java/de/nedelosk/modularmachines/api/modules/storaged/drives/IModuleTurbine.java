package de.nedelosk.modularmachines.api.modules.storaged.drives;

import de.nedelosk.modularmachines.api.modules.energy.IModuleKinetic;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleTurbine extends IModuleDrive, IModuleKinetic{

	boolean isWorking(IModuleState state);

}
