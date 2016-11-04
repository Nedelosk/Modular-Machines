package modularmachines.api.modules.containers;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.state.IModuleState;

public interface IModuleColoredBlock extends IModule {

	int getColor(IModuleState state, int tintIndex);
}
