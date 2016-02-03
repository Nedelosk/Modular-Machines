package de.nedelosk.forestmods.common.modules.storage;

import de.nedelosk.forestmods.api.modules.storage.tanks.IModuleTank;
import de.nedelosk.forestmods.api.utils.ModuleCategoryUIDs;
import de.nedelosk.forestmods.common.modules.ModuleAddable;

public abstract class ModuleTank extends ModuleAddable implements IModuleTank {

	private final int capacity;

	public ModuleTank(String moduleUID, int capacity) {
		super(ModuleCategoryUIDs.TANK, moduleUID);
		this.capacity = capacity;
	}

	@Override
	public int getCapacity() {
		return capacity;
	}
}
