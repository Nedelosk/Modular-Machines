package modularmachines.common.modules.engines;

import net.minecraftforge.energy.IEnergyStorage;

import modularmachines.api.modules.IModuleStorage;
import modularmachines.common.utils.ModuleUtil;

public class ModuleEngineElectric extends ModuleEngine {
	
	public ModuleEngineElectric(IModuleStorage storage, int capacity, int maxTransfer, int energyPerWork, double kineticModifier) {
		super(storage, capacity, maxTransfer, energyPerWork, kineticModifier);
	}

	@Override
	protected boolean canWork() {
		IEnergyStorage energyStorage = ModuleUtil.getEnergy(logic);
		if (energyStorage == null) {
			return false;
		}
		if (energyStorage.getEnergyStored() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected boolean removeMaterial() {
		IEnergyStorage energyStorage = ModuleUtil.getEnergy(logic);
		if (energyStorage == null) {
			return false;
		}
		int energyPerWork = getMaterialPerWork();
		if (energyStorage.extractEnergy(energyPerWork, true) == energyPerWork) {
			return energyStorage.extractEnergy(energyPerWork, false) == energyPerWork;
		} else {
			return false;
		}
	}

	@Override
	protected int getMaterialPerWork() {
		return materialPerWork;
	}

	@Override
	protected double getKineticModifier() {
		return kineticModifier;
	}
}
