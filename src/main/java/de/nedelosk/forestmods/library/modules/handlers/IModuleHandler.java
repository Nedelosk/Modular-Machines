package de.nedelosk.forestmods.library.modules.handlers;

import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modules.IModule;

public interface IModuleHandler {

	IModular getModular();

	IModule getModule();

	String getType();
}
