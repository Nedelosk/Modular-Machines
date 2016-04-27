package de.nedelosk.forestmods.common.modules.storage;

import de.nedelosk.forestmods.common.modules.Module;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modules.IModuleContainer;
import de.nedelosk.forestmods.library.modules.handlers.IModulePage;
import de.nedelosk.forestmods.library.modules.storage.IModuleCapacitor;

public class ModuleCapacitor extends Module implements IModuleCapacitor {

	private final int speedModifier;
	private final int energyModifier;

	public ModuleCapacitor(IModular modular, IModuleContainer container, int speedModifier, int energyModifier) {
		super(modular, container);
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
	protected IModulePage[] createPages() {
		return null;
	}
}
