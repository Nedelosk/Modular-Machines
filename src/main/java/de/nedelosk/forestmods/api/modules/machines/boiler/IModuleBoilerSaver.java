package de.nedelosk.forestmods.api.modules.machines.boiler;

import de.nedelosk.forestmods.api.modules.machines.IModuleMachineSaver;

public interface IModuleBoilerSaver extends IModuleMachineSaver {

	int getHeat();

	int getHeatTotal();

	void setHeat(int heat);

	void addHeat(int heat);

	int getFuel();

	int getFuelTotal();

	void setFuel(int fuel);

	void addFuel(int fuel);
}
