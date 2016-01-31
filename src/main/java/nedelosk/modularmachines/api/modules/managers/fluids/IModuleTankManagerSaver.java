package nedelosk.modularmachines.api.modules.managers.fluids;

import nedelosk.modularmachines.api.modules.fluids.TankData;
import nedelosk.modularmachines.api.modules.managers.IModuleManagerSaver;

public interface IModuleTankManagerSaver extends IModuleManagerSaver {

	TankData getData(int id);

	void setData(int id, TankData data);

	TankData[] getDatas();

	int getUnusedCapacity();

	void setUnusedCapacity(int unusedCapacity);
}
