package nedelosk.modularmachines.api.modules.managers;

import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.modules.special.IModuleWithItem;

public interface IModuleManager<S extends IModuleSaver> extends IModuleWithItem<S> {

	int getMaxTabs();
}
