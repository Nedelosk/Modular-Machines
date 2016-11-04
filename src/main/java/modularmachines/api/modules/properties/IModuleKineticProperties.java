package modularmachines.api.modules.properties;

import modularmachines.api.modules.IModuleProperties;
import modularmachines.api.modules.state.IModuleState;

public interface IModuleKineticProperties extends IModuleProperties {

	double getKineticModifier(IModuleState state);

	int getMaxKineticEnergy(IModuleState state);

	int getMaterialPerWork(IModuleState state);
}
