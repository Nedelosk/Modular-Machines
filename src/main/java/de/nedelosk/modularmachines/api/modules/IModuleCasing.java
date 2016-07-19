package de.nedelosk.modularmachines.api.modules;

import de.nedelosk.modularmachines.api.modules.energy.IModuleHeat;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleCasing extends IModuleTickable, IModuleHeat {

	int getMaxHeat();

	float getResistance(IModuleState state);

	float getHardness(IModuleState state);

	int getHarvestLevel(IModuleState state);

	String getHarvestTool(IModuleState state);
}
