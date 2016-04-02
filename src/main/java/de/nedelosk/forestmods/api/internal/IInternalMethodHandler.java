package de.nedelosk.forestmods.api.internal;

import de.nedelosk.forestmods.api.modules.special.IModuleWithItem;

public interface IInternalMethodHandler {

	/**
	 * Add a module to the module item
	 */
	void addModuleToModuelItem(IModuleWithItem module, IModuleType type);
}
