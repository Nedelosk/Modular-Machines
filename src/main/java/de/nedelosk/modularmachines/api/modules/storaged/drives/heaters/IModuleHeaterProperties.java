package de.nedelosk.modularmachines.api.modules.storaged.drives.heaters;

import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleHeaterProperties extends IModuleProperties {

	double getMaxHeat(IModuleState state);

	int getHeatModifier(IModuleState state);

}
