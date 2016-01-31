package nedelosk.modularmachines.api.modular.basic.container.module;

import java.util.Collection;

import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface IMultiModuleContainer<M extends IModule, S extends IModuleSaver, O extends Collection<ModuleStack<M, S>>> extends IModuleContainer {

	void addStack(ModuleStack<M, S> stack);

	void addStack(int index, ModuleStack<M, S> stack);

	void setStacks(O collection);

	void setStack(ModuleStack<M, S> stack, String moduleUID);

	int getIndex(ModuleStack<M, S> stack);

	O getStacks();

	ModuleStack<M, S> getStack(int index);

	ModuleStack<M, S> getStack(String moduleUID);
}
