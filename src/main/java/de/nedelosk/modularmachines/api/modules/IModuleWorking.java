package de.nedelosk.modularmachines.api.modules;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleWorking extends IModule{

	int getWorkTime(IModuleState state);

	void addWorkTime(IModuleState state, int workTime);

	void setWorkTime(IModuleState state, int workTime);

	int getWorkTimeTotal(IModuleState state);

	void setWorkTimeTotal(IModuleState state, int workTimeTotal);

}
