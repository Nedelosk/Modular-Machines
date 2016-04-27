package de.nedelosk.forestmods.library.modules.heater;


public interface IModuleHeaterBurning extends IModuleHeater {

	int getBurnTime();

	void setBurnTime(int burnTime);

	void addBurnTime(int burnTime);

}
