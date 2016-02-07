package de.nedelosk.forestmods.api.modules.heater;

import de.nedelosk.forestmods.api.modules.IModuleSaver;

public interface IModuleHeaterSaver extends IModuleSaver {

	int getHeat();

	void setHeat(int heat);

	void addHeat(int heat);

	int getBurnTime();

	void setBurnTime(int burnTime);

	void addBurnTime(int burnTime);
}
