package de.nedelosk.modularmachines.api.modules.heater;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleHeaterBurning extends IModuleHeater {

	int getBurnTime(IModuleState state);

	void addBurnTime(IModuleState state, int burntime);

	void setBurnTime(IModuleState state, int burnTime);

}
