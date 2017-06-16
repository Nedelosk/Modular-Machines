package modularmachines.common.energy;

import net.minecraft.nbt.NBTTagCompound;

import modularmachines.api.modules.energy.IKineticSource;

public class KineticBuffer implements IKineticSource {

	protected double kineticEnergy;
	protected final double capacity;
	protected final double maxExtract;
	protected final double maxReceive;

	public KineticBuffer(double capacity, double maxTransfer) {
		this(capacity, maxTransfer, maxTransfer);
	}

	public KineticBuffer(double capacity, double maxReceive, double maxExtract) {
		this.capacity = capacity;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
	}
	
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
    	compound.setDouble("kineticEnergy", kineticEnergy);
    	return compound;
    }
    
    public void readFromNBT(NBTTagCompound compound){
    	kineticEnergy = compound.getDouble("kineticEnergy");
    }

	@Override
	public double extractKineticEnergy(double maxExtract, boolean simulate) {
		double energyExtracted = Math.min(kineticEnergy, Math.min(this.maxExtract, maxExtract));
		if (!simulate) {
			kineticEnergy -= energyExtracted;
		}
		return energyExtracted;
	}

	@Override
	public double receiveKineticEnergy(double maxReceive, boolean simulate) {
		double energyReceived = Math.min(capacity - kineticEnergy, Math.min(this.maxReceive, maxReceive));
		if (!simulate) {
			kineticEnergy += energyReceived;
		}
		return energyReceived;
	}

	@Override
	public void increaseKineticEnergy(double kineticModifier) {
		if (kineticEnergy == capacity) {
			return;
		}
		double step = 0.1D;
		double change = step + (((capacity - kineticEnergy) / capacity) * step * kineticModifier);
		kineticEnergy += change;
		kineticEnergy = Math.min(kineticEnergy, capacity);
	}

	@Override
	public void reduceKineticEnergy(double kineticModifier) {
		if (kineticEnergy == 0) {
			return;
		}
		double step = 0.01D;
		double change = step + ((kineticEnergy / capacity) * step * kineticModifier);
		kineticEnergy -= change;
		kineticEnergy = Math.max(kineticEnergy, 0);
	}

	@Override
	public double getCapacity() {
		return capacity;
	}

	@Override
	public double getStored() {
		return kineticEnergy;
	}
}
