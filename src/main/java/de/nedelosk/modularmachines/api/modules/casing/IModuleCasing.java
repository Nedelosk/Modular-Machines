package de.nedelosk.modularmachines.api.modules.casing;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleCasing extends IModule {

	int getMaxHeat();

	int getHeat(IModuleState state);

	void addHeat(IModuleState state, int heat);

	void setHeat(IModuleState state, int heat);

	int getControllers(IModuleState state);

	int getResistance(IModuleState state);

	int getHardness(IModuleState state);

	boolean canAssembleCasing(IModuleState state);
}
