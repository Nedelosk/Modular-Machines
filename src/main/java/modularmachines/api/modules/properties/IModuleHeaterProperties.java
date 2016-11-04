package modularmachines.api.modules.properties;

import modularmachines.api.modules.IModuleProperties;
import modularmachines.api.modules.state.IModuleState;

public interface IModuleHeaterProperties extends IModuleProperties {

	double getMaxHeat(IModuleState state);

	int getHeatModifier(IModuleState state);
}
