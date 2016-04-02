package de.nedelosk.forestmods.api.producers.handlers;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.utils.ModuleStack;

public interface IModuleBuilder {

	void setModuleStack(ModuleStack moduleStack);

	void setModular(IModular modular);

	IModuleHandler build();
}
