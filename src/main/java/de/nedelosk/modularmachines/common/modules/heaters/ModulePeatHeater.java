package de.nedelosk.modularmachines.common.modules.heaters;

import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public class ModulePeatHeater extends ModuleHeater {

	public ModulePeatHeater() {
		super("peat");
	}

	@Override
	public boolean isWorking(IModuleState state) {
		return false;
	}

	@Override
	public int getColor(IModuleContainer container) {
		return 0;
	}

	@Override
	protected boolean canAddHeat(IModuleState state) {
		return false;
	}

	@Override
	protected boolean updateFuel(IModuleState state) {
		return false;
	}

	@Override
	protected void afterAddHeat(IModuleState state) {
	}
}
