package nedelosk.modularmachines.api.modules.special;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface IModuleController<S extends IModuleSaver> extends IModule<S> {

	boolean canAssembleModular(IModular modular, ModuleStack<IModuleController<S>, S> moduleStack);
}
