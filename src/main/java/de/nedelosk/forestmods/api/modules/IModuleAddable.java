package de.nedelosk.forestmods.api.modules;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.utils.ModularException;
import de.nedelosk.forestmods.api.utils.ModuleStack;

public interface IModuleAddable extends IModuleDefault {

	void onAddInModular(IModular modular, ModuleStack stack) throws ModularException;
}
