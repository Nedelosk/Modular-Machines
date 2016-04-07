package de.nedelosk.forestmods.common.modules.storage;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.handlers.IModulePage;
import de.nedelosk.forestmods.api.modules.storage.IModuleCapacitor;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modules.Module;

public class ModuleCapacitor extends Module implements IModuleCapacitor {

	private final int speedModifier;
	private final int energyModifier;

	public ModuleCapacitor(String name, int speedModifier, int energyModifier) {
		super(name);
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
	public boolean canWork(IModular modular, ModuleStack capacitor) {
		return true;
	}

	@Override
	protected IModulePage[] createPages() {
		return null;
	}
}
