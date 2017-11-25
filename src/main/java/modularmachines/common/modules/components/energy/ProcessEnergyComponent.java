package modularmachines.common.modules.components.energy;

import net.minecraftforge.energy.IEnergyStorage;

import modularmachines.api.modules.components.IProcessConsumerComponent;

public abstract class ProcessEnergyComponent extends ProcessComponent implements IProcessConsumerComponent {
	
	public ProcessEnergyComponent() {
	}
	
	public int getEnergyPerTick() {
		return this.getProcessEnergy() / this.getProcessLength();
	}
	
	@Override
	protected void progressTick() {
		super.progressTick();
		IEnergyStorage storage = provider.getContainer().getInterface(IEnergyStorage.class);
		if (storage == null) {
			return;
		}
		storage.extractEnergy(getEnergyPerTick(), false);
	}
	
	@Override
	public abstract int getProcessLength();
	
	
	public abstract int getProcessEnergy();
}
