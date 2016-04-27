package de.nedelosk.forestmods.library.modules.heater;

import de.nedelosk.forestmods.library.modules.IModule;

public interface IModuleHeater extends IModule {

	void setHeat(int heat);

	void addHeat(int heat);

	int getHeat();

	int getMaxHeat();
}
