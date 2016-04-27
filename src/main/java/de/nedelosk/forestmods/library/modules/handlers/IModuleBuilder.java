package de.nedelosk.forestmods.library.modules.handlers;

import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modules.IModule;

public interface IModuleBuilder<M extends IModule> {

	void setModule(M module);

	void setModular(IModular modular);

	IModuleHandler build();
}
