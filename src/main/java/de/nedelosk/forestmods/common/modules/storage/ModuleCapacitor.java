package de.nedelosk.forestmods.common.modules.storage;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.storage.capacitors.IModuleCapacitor;
import de.nedelosk.forestmods.api.utils.ModuleCategoryUIDs;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modules.ModuleAddable;

public class ModuleCapacitor extends ModuleAddable implements IModuleCapacitor {

	private final int speedModifier;
	private final int energyModifier;

	public ModuleCapacitor(String moduleUID, int speedModifier, int energyModifier) {
		super(ModuleCategoryUIDs.CAPACITOR, moduleUID);
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
}
