package de.nedelosk.forestmods.api.modules.managers.fluids;

import de.nedelosk.forestmods.api.modules.fluids.TankData;
import de.nedelosk.forestmods.api.modules.managers.IModuleManagerSaver;

public interface IModuleTankManagerSaver extends IModuleManagerSaver {

	TankData getData(int id);

	void setData(int id, TankData data);

	TankData[] getDatas();

	int getUnusedCapacity();

	void setUnusedCapacity(int unusedCapacity);
}
