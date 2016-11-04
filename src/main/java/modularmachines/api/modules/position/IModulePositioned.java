package modularmachines.api.modules.position;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.containers.IModuleContainer;

public interface IModulePositioned extends IModule {

	IModulePostion[] getValidPositions(IModuleContainer container);
}
