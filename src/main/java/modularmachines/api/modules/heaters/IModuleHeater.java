package modularmachines.api.modules.heaters;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.ITickable;
import modularmachines.api.modules.properties.IModuleHeaterProperties;
import modularmachines.api.modules.state.IModuleState;

public interface IModuleHeater extends ITickable, IModule, IModuleHeaterProperties {

	boolean isWorking(IModuleState state);
}
