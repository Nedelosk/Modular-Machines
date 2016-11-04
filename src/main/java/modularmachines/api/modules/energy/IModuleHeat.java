package modularmachines.api.modules.energy;

import modularmachines.api.energy.IHeatSource;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.state.IModuleState;

public interface IModuleHeat extends IModule {

	IHeatSource getHeatSource(IModuleState state);
}
