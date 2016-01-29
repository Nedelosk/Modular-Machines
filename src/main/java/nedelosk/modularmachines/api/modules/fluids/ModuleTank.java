package nedelosk.modularmachines.api.modules.fluids;

import nedelosk.modularmachines.api.modules.Module;
import nedelosk.modularmachines.api.utils.ModuleCategoryUIDs;

public abstract class ModuleTank extends Module implements IModuleTank {

	private final int capacity;

	public ModuleTank(String moduleUID, int capacity) {
		super(ModuleCategoryUIDs.TANKS, moduleUID);
		this.capacity = capacity;
	}

	@Override
	public int getCapacity() {
		return capacity;
	}
}
