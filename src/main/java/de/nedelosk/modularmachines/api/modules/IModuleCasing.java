package de.nedelosk.modularmachines.api.modules;

import de.nedelosk.modularmachines.api.energy.IHeatSource;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleCasing extends IModuleTickable {

	IHeatSource getHeatSource(IModuleState state);

	int getMaxHeat();

	float getResistance(IModuleState state);

	float getHardness(IModuleState state);

	int getHarvestLevel(IModuleState state);

	String getHarvestTool(IModuleState state);
}
