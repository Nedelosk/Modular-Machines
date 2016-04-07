package de.nedelosk.forestmods.api.modules.handlers;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.utils.ModuleStack;

public interface IModuleHandler {

	IModular getModular();

	ModuleStack getModuleStack();

	String getType();
}
