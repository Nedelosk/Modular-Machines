package de.nedelosk.forestmods.api.modules.managers;

import de.nedelosk.forestmods.api.modules.IModuleAddable;
import de.nedelosk.forestmods.api.modules.special.IModuleWithItem;

public interface IModuleManager extends IModuleWithItem, IModuleAddable {

	int getMaxTabs();
}
