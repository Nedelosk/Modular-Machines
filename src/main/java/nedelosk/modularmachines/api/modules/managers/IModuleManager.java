package nedelosk.modularmachines.api.modules.managers;

import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.modules.special.IProducerWithItem;

public interface IModuleManager<S extends IModuleSaver> extends IProducerWithItem<S> {

	int getMaxTabs();
}
