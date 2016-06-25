package de.nedelosk.modularmachines.api.modules.casing;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleCasing extends IModule {

	int getMaxHeat();

	int getHeat(IModuleState state);

	void addHeat(IModuleState state, int heat);

	void setHeat(IModuleState state, int heat);

	int getControllers(IModuleState state);

	float getResistance(IModuleState state);

	float getHardness(IModuleState state);

	int getHarvestLevel(IModuleState state);

	String getHarvestTool(IModuleState state);

	boolean canAssembleCasing(IModuleState state);
}
