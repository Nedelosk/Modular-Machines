package de.nedelosk.modularmachines.api.modules.properties;

import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleKineticProperties extends IModuleProperties {

	double getKineticModifier(IModuleState state);

	int getMaxKineticEnergy(IModuleState state);

	int getMaterialPerWork(IModuleState state);
}
