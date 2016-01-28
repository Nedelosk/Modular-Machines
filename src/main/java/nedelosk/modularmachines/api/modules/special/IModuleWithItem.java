package nedelosk.modularmachines.api.modules.special;

import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleSaver;

public interface IModuleWithItem<S extends IModuleSaver> extends IModule<S> {

	int getColor();
}
