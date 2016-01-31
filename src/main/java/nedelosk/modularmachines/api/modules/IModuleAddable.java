package nedelosk.modularmachines.api.modules;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.utils.ModularException;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface IModuleAddable extends IModuleDefault {

	void onAddInModular(IModular modular, ModuleStack stack) throws ModularException;
}
