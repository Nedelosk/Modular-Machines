package de.nedelosk.forestmods.api.internal;

import de.nedelosk.forestmods.api.modular.material.Materials.Material;
import de.nedelosk.forestmods.api.modules.IModuleType;
import de.nedelosk.forestmods.api.modules.special.IModuleWithItem;

public class DummyInternalMethodHandler implements IInternalMethodHandler {

	@Override
	public void addModuleToModuelItem(IModuleWithItem module, IModuleType type, Material material) {
	}
}
