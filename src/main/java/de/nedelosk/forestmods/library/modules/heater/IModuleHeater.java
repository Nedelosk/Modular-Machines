package de.nedelosk.forestmods.library.modules.heater;

import de.nedelosk.forestmods.library.modules.IModuleController;

public interface IModuleHeater extends IModuleController {

	void setHeat(int heat);

	void addHeat(int heat);

	int getHeat();

	int getMaxHeat();
}
