package de.nedelosk.forestmods.library.modules.generator;

import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.modules.IModuleColored;
import de.nedelosk.forestmods.library.modules.IModuleController;

public interface IModuleGenerator extends IModule, IModuleColored, IModuleController {

	int getEnergy();
}
