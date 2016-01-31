package nedelosk.modularmachines.api.modules.basic;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface IModuleUpdatable extends IModule {

	void updateServer(IModular modular, ModuleStack stack);

	void updateClient(IModular modular, ModuleStack stack);
}
