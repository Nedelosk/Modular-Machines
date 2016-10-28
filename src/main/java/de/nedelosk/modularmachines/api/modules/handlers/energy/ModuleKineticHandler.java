package de.nedelosk.modularmachines.api.modules.handlers.energy;

import de.nedelosk.modularmachines.api.energy.IKineticSource;
import de.nedelosk.modularmachines.api.modules.handlers.BlankModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class ModuleKineticHandler extends BlankModuleContentHandler implements IKineticSource, INBTSerializable<NBTTagCompound> {

	protected double kineticEnergy;
	protected final double capacity;
	protected final double maxExtract;
	protected final double maxReceive;

	public ModuleKineticHandler(IModuleState moduleState, double capacity, double maxTransfer) {
		this(moduleState, capacity, maxTransfer, maxTransfer);
	}

	public ModuleKineticHandler(IModuleState moduleState, double capacity, double maxReceive, double maxExtract) {
		super(moduleState, "KineticHandler");
		this.capacity = capacity;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		kineticEnergy = nbt.getDouble("kineticEnergy");
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
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
