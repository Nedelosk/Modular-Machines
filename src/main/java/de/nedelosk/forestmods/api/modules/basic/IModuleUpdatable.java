package de.nedelosk.forestmods.api.modules.basic;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.utils.ModuleStack;

public interface IModuleUpdatable extends IModule {

	void updateServer(IModular modular, ModuleStack stack);

	void updateClient(IModular modular, ModuleStack stack);
}
