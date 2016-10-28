package de.nedelosk.modularmachines.api.modules.position;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.containers.IModuleContainer;

public interface IModulePositioned extends IModule {

	IModulePostion[] getValidPositions(IModuleContainer container);
}
