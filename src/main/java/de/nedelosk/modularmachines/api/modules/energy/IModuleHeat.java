package de.nedelosk.modularmachines.api.modules.energy;

import de.nedelosk.modularmachines.api.energy.IHeatSource;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleHeat extends IModule {

	IHeatSource getHeatSource(IModuleState state);
}
