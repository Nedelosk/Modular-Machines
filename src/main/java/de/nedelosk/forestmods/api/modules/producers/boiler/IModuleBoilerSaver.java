package de.nedelosk.forestmods.api.modules.producers.boiler;

public interface IModuleBoilerSaver extends IModuleSaver {

	int getHeat();

	int getHeatTotal();

	void setHeat(int heat);

	void addHeat(int heat);

	int getFuel();

	int getFuelTotal();

	void setFuel(int fuel);

	void addFuel(int fuel);
}
