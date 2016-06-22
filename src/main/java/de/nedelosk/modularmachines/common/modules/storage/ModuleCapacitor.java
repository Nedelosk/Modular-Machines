package de.nedelosk.modularmachines.common.modules.storage;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IModuleCapacitor;
import de.nedelosk.modularmachines.common.modules.Module;

public class ModuleCapacitor extends Module implements IModuleCapacitor {

	private final int speedModifier;
	private final int energyModifier;

	public ModuleCapacitor(int speedModifier, int energyModifier) {
		super();
		this.speedModifier = speedModifier;
		this.energyModifier = energyModifier;
	}

	@Override
	public int getSpeedModifier() {
		return speedModifier;
	}

	@Override
	public int getEnergyModifier() {
		return energyModifier;
	}

	@Override
	public boolean canWork(IModular modular) {
		return true;
	}

	@Override
	public IModulePage[] createPages(IModuleState state) {
		return null;
	}
}
