package de.nedelosk.modularmachines.api.modules.energy;

import de.nedelosk.modularmachines.api.energy.IEnergyType;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleEnergy extends IModule {
	/**
	 * @return The energy type of the module.
	 */
	IEnergyType getEnergyType(IModuleState state);

}
