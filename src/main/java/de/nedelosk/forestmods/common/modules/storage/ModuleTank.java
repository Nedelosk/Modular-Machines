package de.nedelosk.forestmods.common.modules.storage;

import de.nedelosk.forestmods.api.modules.storage.tanks.IModuleTank;
import de.nedelosk.forestmods.api.utils.ModuleCategoryUIDs;
import de.nedelosk.forestmods.common.modules.ModuleAddable;

public abstract class ModuleTank extends ModuleAddable implements IModuleTank {

	public ModuleTank(String moduleUID) {
		super(ModuleCategoryUIDs.TANK, moduleUID);
	}
}
