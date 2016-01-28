package nedelosk.modularmachines.api.modules.fluids;

import nedelosk.modularmachines.api.modules.Module;

public abstract class ModuleTank extends Module implements IModuleTank {

	private final int capacity;

	public ModuleTank(String categoryUID, String moduleUID, int capacity) {
		super(categoryUID, moduleUID);
		this.capacity = capacity;
	}

	@Override
	public int getCapacity() {
		return capacity;
	}
}
