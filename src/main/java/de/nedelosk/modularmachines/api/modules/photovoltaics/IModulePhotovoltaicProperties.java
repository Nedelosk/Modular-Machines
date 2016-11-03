package de.nedelosk.modularmachines.api.modules.photovoltaics;

import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModulePhotovoltaicProperties extends IModuleProperties {

	int getEnergyModifier(IModuleState state);
}
