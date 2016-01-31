package nedelosk.modularmachines.api.modules.storage.tanks;

import nedelosk.modularmachines.api.modules.ModuleAddable;
import nedelosk.modularmachines.api.utils.ModuleCategoryUIDs;

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
