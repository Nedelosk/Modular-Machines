package nedelosk.modularmachines.api.modular.basic.container.module;

import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface ISingleModuleContainer<M extends IModule<S>, S extends IModuleSaver> extends IModuleContainer {

	void setStack(ModuleStack<M, S> stack);

	ModuleStack<M, S> getStack();
}
