package de.nedelosk.modularmachines.api.modules.handlers.energy;

import de.nedelosk.modularmachines.api.energy.EnergyRegistry;
import de.nedelosk.modularmachines.api.energy.IHeatLevel;
import de.nedelosk.modularmachines.api.energy.IHeatSource;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleHeatBuffer implements IHeatSource, IModuleContentHandler {

	protected double heatBuffer;
	protected double capacity;
	protected double maxExtract;
	protected double maxReceive;
	protected IHeatLevel level;
	protected IModuleState state;

	public ModuleHeatBuffer(IModuleState state, float capacity, float maxTransfer) {
		this(state, capacity, maxTransfer, maxTransfer);
	}

	public ModuleHeatBuffer(IModuleState state, float capacity, float maxReceive, float maxExtract) {
		this.state = state;
		this.capacity = capacity;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
		this.heatBuffer = EnergyRegistry.COLD_TEMP;
	}

	@Override
	public IModuleState getModuleState() {
		return state;
	}

	@Override
	public String getHandlerUID() {
		return "HeatBuffer";
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		heatBuffer = nbt.getDouble("Heat");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setDouble("Heat", heatBuffer);
		return nbt;
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
	public void increaseHeat(int heatModifier) {
		if (heatBuffer == capacity) {
			return;
		}
		double step = getHeatLevel().getHeatStepUp();
		double change = step + (((capacity - heatBuffer) / capacity) * step * heatModifier);
		heatBuffer += change;
		heatBuffer = Math.min(heatBuffer, capacity);
	}

	@Override
	public void reduceHeat(int heatModifier) {
		if (heatBuffer == EnergyRegistry.COLD_TEMP) {
			return;
		}
		double step = getHeatLevel().getHeatStepDown();
		double change = step + ((heatBuffer / capacity) * step * heatModifier);
		heatBuffer -= change;
		heatBuffer = Math.max(heatBuffer,  EnergyRegistry.COLD_TEMP);
	}

	@Override
	public IHeatLevel getHeatLevel() {
		return EnergyRegistry.getHeatLevel(heatBuffer);
	}

	@Override
	public double getHeatStored() {
		return heatBuffer;
	}

	@Override
	public double getCapacity() {
		return capacity;
	}
}
