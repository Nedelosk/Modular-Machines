package de.nedelosk.modularmachines.api.modules.items;

import de.nedelosk.modularmachines.api.modules.IModule;

public interface IModuleColoredItem extends IModule {

	int getColor(IModuleContainer container);
}
