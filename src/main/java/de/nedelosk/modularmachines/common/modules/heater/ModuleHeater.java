package de.nedelosk.modularmachines.common.modules.heater;

import de.nedelosk.modularmachines.api.modules.heater.IModuleHeater;
import de.nedelosk.modularmachines.common.modules.Module;

public abstract class ModuleHeater extends Module implements IModuleHeater {

	protected final int maxHeat;

	public ModuleHeater(int maxHeat) {
		super();
		this.maxHeat = maxHeat;
	}

	@Override
	public int getMaxHeat() {
		return maxHeat;
	}
}