package modularmachines.api.modules;

import modularmachines.api.modules.state.IModuleState;

public interface IModuleBurning extends IModule {

	int getBurnTimeTotal(IModuleState state);

	int getBurnTime(IModuleState state);

	void addBurnTime(IModuleState state, int burntime);

	void setBurnTime(IModuleState state, int burnTime);
}
