package de.nedelosk.forestmods.api.modules.basic;

import de.nedelosk.forestmods.api.modules.IModuleDefault;

public interface IModuleCasing extends IModuleDefault, IModuleWithRenderer {

	int getMaxHeat();

	int getResistance();
}
