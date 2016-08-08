package de.nedelosk.modularmachines.api.modules.integration;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;

public interface IModuleJEI extends IModule {

	String[] getJEIRecipeCategorys(IModuleContainer container);

}
