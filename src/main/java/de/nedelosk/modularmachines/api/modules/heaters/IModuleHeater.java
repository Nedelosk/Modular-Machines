package de.nedelosk.modularmachines.api.modules.heaters;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.ITickable;
import de.nedelosk.modularmachines.api.modules.properties.IModuleHeaterProperties;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleHeater extends ITickable, IModule, IModuleHeaterProperties {

	boolean isWorking(IModuleState state);
}
