package de.nedelosk.modularmachines.api.modules.handlers.energy;

import de.nedelosk.modularmachines.api.energy.IEnergyBuffer;
import de.nedelosk.modularmachines.api.modules.handlers.BlankModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.INBTSerializable;

public class ModuleEnergyBuffer extends BlankModuleContentHandler implements IEnergyBuffer, INBTSerializable<NBTTagCompound>{

	protected final long capacity;
	protected final long maxReceive;
	protected final long maxExtract;
	protected final int tier;
	protected long energy;

	public ModuleEnergyBuffer(IModuleState moduleState, int capacity, int tier) {
		this(moduleState, capacity, capacity, capacity, tier);
	}

	public ModuleEnergyBuffer(IModuleState moduleState, int capacity, int maxTransfer, int tier) {
		this(moduleState, capacity, maxTransfer, maxTransfer, tier);
	}

	public ModuleEnergyBuffer(IModuleState moduleState, int capacity, int maxReceive, int maxExtract, int tier) {
		super(moduleState, "EnergyBuffer");
		this.tier = tier;
		this.capacity = capacity;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
	}

	@Override
	public void setEnergy(long energy) {
		this.energy = energy;

		if (this.energy > capacity) {
			this.energy = capacity;
		} else if (this.energy < 0) {
			this.energy = 0;
		}
	}

	@Override
	public long receiveEnergy(IModuleState state, EnumFacing facing, long maxReceive, boolean simulate) {
		long energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));

		if (!simulate) {
			energy += energyReceived;
		}
		return energyReceived;
	}

	@Override
	public long extractEnergy(IModuleState state, EnumFacing facing, long maxExtract, boolean simulate) {
		long energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));

		if (!simulate) {
			energy -= energyExtracted;
		}
		return energyExtracted;
	}

	@Override
	public long getEnergyStored() {
		return energy;
	}

	@Override
	public long getCapacity() {
		return capacity;
	}

	@Override
	public int getTier() {
		return tier;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		if (energy < 0) {
			energy = 0;
		}
		nbt.setLong("Energy", energy);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		this.energy = nbt.getLong("Energy");

		if (energy > capacity) {
			energy = capacity;
		}
	}

	@Override
	public boolean canExtract() {
		return true;
	}

	@Override
	public boolean canReceive() {
		return true;
	}
}
