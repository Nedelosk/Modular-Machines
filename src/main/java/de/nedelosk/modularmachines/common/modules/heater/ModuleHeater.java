package de.nedelosk.modularmachines.common.modules.heater;

import de.nedelosk.modularmachines.api.modules.heater.IModuleHeater;
import de.nedelosk.modularmachines.common.modules.Module;

public abstract class ModuleHeater extends Module implements IModuleHeater {

	protected final int maxHeat;
	protected final int size;

	public ModuleHeater(int maxHeat, int size) {
		super();
		this.maxHeat = maxHeat;
		this.size = size;
	}

	@Override
	public int getMaxHeat() {
		return maxHeat;
	}
	
	@Override
	public int getSize() {
		return size;
	}
}