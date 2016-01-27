package nedelosk.modularmachines.api.modular.basic.container.module;

import java.util.Collection;

import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface IMultiModuleContainer<P extends IModule, O extends Collection<ModuleStack<P>>> extends IModuleContainer {

	void addStack(ModuleStack<P> stack);

	void addStack(int index, ModuleStack<P> stack);

	void setStacks(O collection);

	int getIndex(ModuleStack<P> stack);

	O getStacks();

	ModuleStack<P> getStack(int index);

	ModuleStack<P> getStack(String moduleUID);
}
