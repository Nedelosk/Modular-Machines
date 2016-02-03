package de.nedelosk.forestmods.api.modules.container;

import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.utils.ModuleStack;

public interface ISingleModuleContainer<M extends IModule, S extends IModuleSaver> extends IModuleContainer {

	void setStack(ModuleStack<M, S> stack);

	ModuleStack<M, S> getStack();
}
