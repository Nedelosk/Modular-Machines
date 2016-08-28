package de.nedelosk.modularmachines.api.modules.heaters;

import de.nedelosk.modularmachines.api.modules.IModuleTickable;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleHeater extends IModuleTickable, IModuleHeaterProperties {

	boolean isWorking(IModuleState state);
}
