package de.nedelosk.modularmachines.api.modules.items;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleColoredBlock extends IModule {

	int getColor(IModuleState state, int tintIndex);
}
