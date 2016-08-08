package de.nedelosk.modularmachines.api.modules.storaged;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleModuleStorage extends IModule {

	int getAllowedComplexity(IModuleState state);

	EnumPosition getCurrentPosition(IModuleState state);

	boolean canUseFor(EnumPosition position, IModuleContainer container);

}
