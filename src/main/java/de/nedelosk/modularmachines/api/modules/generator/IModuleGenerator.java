package de.nedelosk.modularmachines.api.modules.generator;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleColored;
import de.nedelosk.modularmachines.api.modules.IModuleController;

public interface IModuleGenerator extends IModule, IModuleColored, IModuleController {

	int getEnergy();
}
