package nedelosk.modularmachines.api.modular.basic.container.module;

import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface ISingleModuleContainer<P extends IModule> extends IModuleContainer {

	void setStack(ModuleStack<P> stack);

	ModuleStack<P> getStack();
}
