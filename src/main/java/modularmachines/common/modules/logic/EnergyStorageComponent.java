package modularmachines.common.modules.logic;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.energy.IEnergyStorage;

import modularmachines.api.modules.logic.LogicComponent;

public class EnergyStorageComponent extends LogicComponent implements IEnergyStorage {
	
	public final List<IEnergyStorage> energyStorages;
	
	public EnergyStorageComponent() {
		this.energyStorages = new ArrayList<>();
	}
	
	public void addStorage(IEnergyStorage storage) {
		if (!energyStorages.contains(storage)) {
			energyStorages.add(storage);
		}
	}
	
	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		int totalExtract = 0;
		for (IEnergyStorage energyStorage : energyStorages) {
			int extract = energyStorage.extractEnergy(maxExtract, simulate);
			totalExtract += extract;
			maxExtract -= extract;
			if (maxExtract <= 0) {
				break;
			}
		}
		return totalExtract;
	}
	
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		int totalReceived = 0;
		for (IEnergyStorage energyStorage : energyStorages) {
			int receive = energyStorage.receiveEnergy(maxReceive, simulate);
			totalReceived += receive;
			maxReceive -= receive;
			if (maxReceive <= 0) {
				break;
			}
			break;
		}
		return totalReceived;
	}
	
	@Override
	public int getEnergyStored() {
		int energyStored = 0;
		for (IEnergyStorage energyStorage : energyStorages) {
			energyStored += energyStorage.getEnergyStored();
		}
		return energyStored;
	}
	
	@Override
	public int getMaxEnergyStored() {
		int capacity = 0;
		for (IEnergyStorage energyStorage : energyStorages) {
			capacity += energyStorage.getMaxEnergyStored();
		}
		return capacity;
	}
	
	@Override
	public boolean canExtract() {
		return !energyStorages.isEmpty();
	}
	
	@Override
	public boolean canReceive() {
		return !energyStorages.isEmpty();
	}
}