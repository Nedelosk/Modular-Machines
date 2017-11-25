package modularmachines.common.energy;

import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

import net.minecraftforge.common.util.INBTSerializable;

import modularmachines.api.components.INetworkComponent;
import modularmachines.api.modules.energy.HeatLevel;
import modularmachines.api.modules.energy.IHeatSource;

public class HeatBuffer implements IHeatSource, INBTSerializable<NBTTagCompound>, INetworkComponent {
	
	protected double heatBuffer;
	protected final double capacity;
	protected final double maxExtract;
	protected final double maxReceive;
	protected IHeatListener listener = () -> {
	};
	
	public HeatBuffer(float capacity, float maxTransfer) {
		this(capacity, maxTransfer, maxTransfer);
	}
	
	public HeatBuffer(float capacity, float maxReceive, float maxExtract) {
		this.capacity = capacity;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
		this.heatBuffer = HeatManager.COLD_TEMP;
	}
	
	public void setListener(IHeatListener listener) {
		this.listener = listener;
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
			listener.onChangeHeat();
		}
		return energyExtracted;
	}
	
	@Override
	public double receiveHeat(double maxReceive, boolean simulate) {
		double energyReceived = Math.min(capacity - heatBuffer, Math.min(this.maxReceive, maxReceive));
		if (!simulate) {
			heatBuffer += energyReceived;
			listener.onChangeHeat();
		}
		return energyReceived;
	}
	
	@Override
	public void increaseHeat(double maxHeat, int heatModifier) {
		double max = maxHeat;
		if (maxHeat == -1) {
			max = capacity;
		}
		if (heatBuffer == max) {
			return;
		}
		double step = getHeatLevel().getHeatStepUp();
		double change = step + (((capacity - heatBuffer) / capacity) * step * heatModifier);
		heatBuffer += change;
		heatBuffer = Math.min(heatBuffer, capacity);
		listener.onChangeHeat();
	}
	
	@Override
	public void reduceHeat(int heatModifier) {
		if (heatBuffer == HeatManager.COLD_TEMP) {
			return;
		}
		double step = getHeatLevel().getHeatStepDown();
		double change = step + ((heatBuffer / capacity) * step * heatModifier);
		heatBuffer -= change;
		heatBuffer = Math.max(heatBuffer, HeatManager.COLD_TEMP);
		listener.onChangeHeat();
	}
	
	public HeatLevel getHeatLevel() {
		return HeatManager.getHeatLevel(heatBuffer);
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
	
	@Override
	public void writeData(PacketBuffer data) {
		data.writeDouble(heatBuffer);
	}
	
	@Override
	public void readData(PacketBuffer data) throws IOException {
		heatBuffer = data.readDouble();
	}
}
