package de.nedelosk.modularmachines.api.modules.properties;

import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleHeaterProperties extends IModuleProperties {

	double getMaxHeat(IModuleState state);

	int getHeatModifier(IModuleState state);

}
