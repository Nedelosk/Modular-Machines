package de.nedelosk.forestmods.common.core;

import de.nedelosk.forestmods.api.internal.IInternalMethodHandler;
import de.nedelosk.forestmods.api.modules.special.IModuleWithItem;
import de.nedelosk.forestmods.common.items.ItemModule;

public class InternalMethodHandler implements IInternalMethodHandler {

	@Override
	public void addModuleToModuelItem(IModuleWithItem module, IModuleType type) {
		ItemModule.addModule(module, type);
	}
}
