package de.nedelosk.forestmods.api.modules.special;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.utils.ModuleStack;

public interface IModuleController extends IModule {

	boolean canAssembleModular(IModular modular, ModuleStack<IModuleController, IModuleSaver> moduleStack);
}