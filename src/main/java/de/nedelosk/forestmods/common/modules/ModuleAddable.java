package de.nedelosk.forestmods.common.modules;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.IModuleAddable;
import de.nedelosk.forestmods.api.utils.ModularException;
import de.nedelosk.forestmods.api.utils.ModuleStack;

public abstract class ModuleAddable extends ModuleDefault implements IModuleAddable {

	public ModuleAddable(String categoryUID, String moduleUID) {
		super(categoryUID, moduleUID);
	}

	@Override
	public void onAddInModular(IModular modular, ModuleStack stack) throws ModularException {
	}
}
