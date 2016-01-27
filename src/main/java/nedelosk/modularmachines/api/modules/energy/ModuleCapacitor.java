package nedelosk.modularmachines.api.modules.energy;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.Module;
import nedelosk.modularmachines.api.utils.ModuleStack;

public class ModuleCapacitor extends Module implements IModuleCapacitor {

	private final int speedModifier;
	private final int energyModifier;

	public ModuleCapacitor(String modifier, int speedModifier, int energyModifier) {
		super(modifier);
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
