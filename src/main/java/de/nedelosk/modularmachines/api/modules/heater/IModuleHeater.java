package de.nedelosk.modularmachines.api.modules.heater;

import de.nedelosk.modularmachines.api.modules.IModuleDrive;

public interface IModuleHeater extends IModuleDrive {

	int getMaxHeat();

	int getSize();
}
