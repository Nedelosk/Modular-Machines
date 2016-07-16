package de.nedelosk.modularmachines.api.modules.storaged.drives.heaters;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.drives.IModuleDrive;

public interface IModuleHeater extends IModuleDrive {

	int getMaxHeat();
	
	boolean isWorking(IModuleState state);
}
