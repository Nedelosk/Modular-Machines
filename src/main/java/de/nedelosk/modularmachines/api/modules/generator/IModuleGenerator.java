package de.nedelosk.modularmachines.api.modules.generator;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleColored;

public interface IModuleGenerator extends IModule, IModuleColored {

	int getEnergy();
}
