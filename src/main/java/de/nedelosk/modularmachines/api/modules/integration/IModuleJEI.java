package de.nedelosk.modularmachines.api.modules.integration;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.containers.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleJEI extends IModule {

	String[] getJEIRecipeCategorys(IModuleContainer container);

	void openJEI(IModuleState state);
}
