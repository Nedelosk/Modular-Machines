package nedelosk.modularmachines.api.modules.basic;

import nedelosk.modularmachines.api.modules.IModuleDefault;

public interface IModuleCasing extends IModuleDefault, IModuleWithRenderer {

	int getMaxHeat();

	int getResistance();
}
