package nedelosk.modularmachines.api.modules.managers;

import nedelosk.modularmachines.api.modules.IModuleAddable;
import nedelosk.modularmachines.api.modules.special.IModuleWithItem;

public interface IModuleManager extends IModuleWithItem, IModuleAddable {

	int getMaxTabs();
}
