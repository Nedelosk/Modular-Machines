package de.nedelosk.modularmachines.api.modules.handlers.energy;

import de.nedelosk.modularmachines.api.energy.IKineticSource;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleKineticHandler implements IKineticSource, IModuleContentHandler {

	protected double kineticEnergy;
	protected double capacity;
	protected double maxExtract;
	protected double maxReceive;
	protected IModuleState state;

	public ModuleKineticHandler(IModuleState state, double capacity, double maxTransfer) {
		this(state, capacity, maxTransfer, maxTransfer);
	}

	public ModuleKineticHandler(IModuleState state, double capacity, double maxReceive, double maxExtract) {
		this.state = state;
		this.capacity = capacity;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
	}

	@Override
	public IModuleState getModuleState() {
		return state;
	}

	@Override
	public String getUID() {
		return "KineticHandler";
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		kineticEnergy = nbt.getDouble("kineticEnergy");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setDouble("kineticEnergy", kineticEnergy);
		return nbt;
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
		kineticEnergy = Math.max(kineticEnergy,  0);
	}

	@Override
	public double getCapacity() {
		return capacity;
	}

	@Override
	public double getKineticEnergyStored() {
		return kineticEnergy;
	}
}
