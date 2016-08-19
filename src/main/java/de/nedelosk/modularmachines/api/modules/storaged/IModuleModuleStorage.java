package de.nedelosk.modularmachines.api.modules.storaged;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleModuleStorage extends IModule {

	int getAllowedComplexity(IModuleContainer container);

	EnumStoragePosition getCurrentPosition(IModuleState state);

	boolean canUseFor(EnumStoragePosition position, IModuleContainer container);

}
