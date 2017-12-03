package modularmachines.common.energy;

import net.minecraftforge.energy.EnergyStorage;

public class EnergyStorageBasic extends EnergyStorage {
	public EnergyStorageBasic(int capacity) {
		super(capacity);
	}
	
	public EnergyStorageBasic(int capacity, int maxTransfer) {
		super(capacity, maxTransfer);
	}
	
	public EnergyStorageBasic(int capacity, int maxReceive, int maxExtract) {
		super(capacity, maxReceive, maxExtract);
	}
	
	public void setEnergy(int energy) {
		this.energy = energy;
	}
}
