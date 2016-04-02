package de.nedelosk.forestmods.common.modules.storage;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.storage.capacitors.IModuleCapacitor;
import de.nedelosk.forestmods.api.producers.handlers.IModulePage;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.producers.Producer;

public class ModuleCapacitor extends Producer implements IModuleCapacitor {

	private final int speedModifier;
	private final int energyModifier;

	public ModuleCapacitor(int speedModifier, int energyModifier) {
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
