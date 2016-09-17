package de.nedelosk.modularmachines.api.modules.position;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;

public interface IModulePositioned extends IModule {

	IModulePostion[] getValidPositions(IModuleContainer container);

}
