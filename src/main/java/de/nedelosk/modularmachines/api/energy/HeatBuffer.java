package de.nedelosk.modularmachines.api.energy;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class HeatBuffer implements IHeatSource, INBTSerializable<NBTTagCompound> {

	protected double heatBuffer;
	protected double capacity;
	protected double maxExtract;
	protected double maxReceive;

	public HeatBuffer(float capacity, float maxTransfer) {
		this(capacity, maxTransfer, maxTransfer);
	}

	public HeatBuffer(float capacity, float maxReceive, float maxExtract) {
		this.capacity = capacity;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
		this.heatBuffer = ModularMachinesApi.COLD_TEMP;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setDouble("Heat", heatBuffer);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		heatBuffer = nbt.getDouble("Heat");
	}

	@Override
	public double extractHeat(double maxExtract, boolean simulate) {
		double energyExtracted = Math.min(heatBuffer, Math.min(this.maxExtract, maxExtract));

		if (!simulate) {
			heatBuffer -= energyExtracted;
		}
		return energyExtracted;
	}

	@Override
	public double receiveHeat(double maxReceive, boolean simulate) {
		double energyReceived = Math.min(capacity - heatBuffer, Math.min(this.maxReceive, maxReceive));

		if (!simulate) {
			heatBuffer += energyReceived;
		}
		return energyReceived;
	}

	@Override
	public void increaseHeat(double maxHeat, int heatModifier) {
		double max = maxHeat;
		if(maxHeat == -1){
			max = capacity;
		}
		if (heatBuffer == max) {
			return;
		}
		double step = getHeatLevel().getHeatStepUp();
		double change = step + (((capacity - heatBuffer) / capacity) * step * heatModifier);
		heatBuffer += change;
		heatBuffer = Math.min(heatBuffer, capacity);
	}

	@Override
	public void reduceHeat(int heatModifier) {
		if (heatBuffer == ModularMachinesApi.COLD_TEMP) {
			return;
		}
		double step = getHeatLevel().getHeatStepDown();
		double change = step + ((heatBuffer / capacity) * step * heatModifier);
		heatBuffer -= change;
		heatBuffer = Math.max(heatBuffer,  ModularMachinesApi.COLD_TEMP);
	}

	@Override
	public HeatLevel getHeatLevel() {
		return ModularMachinesApi.getHeatLevel(heatBuffer);
	}

	@Override
	public double getHeatStored() {
		return heatBuffer;
	}

	@Override
	public void setHeatStored(double heatBuffer) {
		this.heatBuffer = heatBuffer;
	}

	@Override
	public double getCapacity() {
		return capacity;
	}
}
