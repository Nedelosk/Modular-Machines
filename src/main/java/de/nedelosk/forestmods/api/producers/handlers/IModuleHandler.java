package de.nedelosk.forestmods.api.producers.handlers;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.utils.ModuleStack;

public interface IModuleHandler {

	IModular getModular();

	ModuleStack getModuleStack();
}
