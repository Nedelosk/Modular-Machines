package de.nedelosk.modularmachines.api.modules.handlers.energy;

import java.util.List;

import de.nedelosk.modularmachines.api.energy.IEnergyBuffer;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.INBTSerializable;

public class ModuleEnergyBuffer implements IEnergyBuffer, IModuleContentHandler, INBTSerializable<NBTTagCompound>{

	protected final IModuleState state;
	protected final long capacity;
	protected final long maxReceive;
	protected final long maxExtract;
	protected final int tier;
	protected long energy;

	public ModuleEnergyBuffer(IModuleState state, int capacity, int tier) {
		this(state, capacity, capacity, capacity, tier);
	}

	public ModuleEnergyBuffer(IModuleState state, int capacity, int maxTransfer, int tier) {
		this(state, capacity, maxTransfer, maxTransfer, tier);
	}

	public ModuleEnergyBuffer(IModuleState state, int capacity, int maxReceive, int maxExtract, int tier) {
		this.state = state;
		this.tier = tier;
		this.capacity = capacity;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
	}

	@Override
	public void loadEnergy(long energy) {
		this.energy = energy;

		if (this.energy > capacity) {
			this.energy = capacity;
		} else if (this.energy < 0) {
			this.energy = 0;
		}
	}

	@Override
	public long receiveEnergy(EnumFacing facing, long maxReceive, boolean simulate) {
		long energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));

		if (!simulate) {
			energy += energyReceived;
		}
		return energyReceived;
	}

	@Override
	public long extractEnergy(EnumFacing facing, long maxExtract, boolean simulate) {
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
	public IModuleState getModuleState() {
		return state;
	}

	@Override
	public String getUID() {
		return "EnergyBuffer";
	}

	@Override
	public void addToolTip(List<String> tooltip, ItemStack stack, IModuleState state) {
	}
}
