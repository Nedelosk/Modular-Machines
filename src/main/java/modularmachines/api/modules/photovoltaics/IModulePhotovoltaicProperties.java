package modularmachines.api.modules.photovoltaics;

import modularmachines.api.modules.IModuleProperties;
import modularmachines.api.modules.state.IModuleState;

public interface IModulePhotovoltaicProperties extends IModuleProperties {

	int getEnergyModifier(IModuleState state);
}
