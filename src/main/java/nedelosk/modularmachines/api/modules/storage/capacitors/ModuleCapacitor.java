package nedelosk.modularmachines.api.modules.storage.capacitors;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.ModuleAddable;
import nedelosk.modularmachines.api.utils.ModuleCategoryUIDs;
import nedelosk.modularmachines.api.utils.ModuleStack;

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
