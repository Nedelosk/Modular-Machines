package nedelosk.modularmachines.api.modules.special;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface IModuleController<S extends IModuleSaver> extends IModule<S> {

	boolean canBuildModular(IModular modular, ModuleStack<IModuleController> moduleStack);
}
