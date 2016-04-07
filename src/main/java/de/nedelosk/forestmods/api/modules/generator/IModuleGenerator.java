package de.nedelosk.forestmods.api.modules.generator;

import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.modules.special.IModuleController;
import de.nedelosk.forestmods.api.modules.special.IModuleWithItem;

public interface IModuleGenerator extends IModule, IModuleWithItem, IModuleController {

	int getEnergy();
}
