package modularmachines.api.modules;

import modularmachines.api.modules.state.IModuleState;

public interface IModuleWorkerTime extends IModuleWorker {

	int getWorkTime(IModuleState state);

	void setWorkTime(IModuleState state, int burnTime);

	void addWorkTime(IModuleState state, int burnTime);

	int getWorkTimeTotal(IModuleState state);

	void setWorkTimeTotal(IModuleState state, int burnTimeTotal);
}
