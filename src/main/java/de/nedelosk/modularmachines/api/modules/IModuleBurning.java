package de.nedelosk.modularmachines.api.modules;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleBurning extends IModule{

	int getBurnTime(IModuleState state);

	void addBurnTime(IModuleState state, int burntime);

	void setBurnTime(IModuleState state, int burnTime);
}
