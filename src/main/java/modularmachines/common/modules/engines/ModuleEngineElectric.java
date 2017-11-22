package modularmachines.common.modules.engines;

/*public class ModuleEngineElectric extends ModuleEngine {
	
	public ModuleEngineElectric(int capacity, int maxTransfer, int energyPerWork, double kineticModifier) {
		super(capacity, maxTransfer, energyPerWork, kineticModifier);
	}
	
	@Override
	protected boolean canWork() {
		IEnergyStorage energyStorage = ModuleUtil.getEnergy(container);
		if (energyStorage == null) {
			return false;
		}
		return energyStorage.getEnergyStored() > 0;
	}
	
	@Override
	protected boolean removeMaterial() {
		IEnergyStorage energyStorage = ModuleUtil.getEnergy(container);
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
}*/
