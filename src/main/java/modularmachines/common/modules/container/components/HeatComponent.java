package modularmachines.common.modules.container.components;

import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

import modularmachines.api.components.INetworkComponent;
import modularmachines.api.modules.INBTReadable;
import modularmachines.api.modules.INBTWritable;
import modularmachines.api.modules.container.ContainerComponent;
import modularmachines.api.modules.energy.HeatLevel;
import modularmachines.api.modules.energy.IHeatSource;
import modularmachines.common.energy.HeatBuffer;
import modularmachines.common.energy.IHeatListener;
import modularmachines.common.utils.TickHelper;

public class HeatComponent extends ContainerComponent implements IHeatSource, INetworkComponent, INBTReadable, INBTWritable, IHeatListener {
	
	protected final HeatBuffer buffer;
	protected double lastHeat;
	private TickHelper tickHelper = new TickHelper();
	
	public HeatComponent() {
		buffer = new HeatBuffer(500, 15F);
		buffer.setListener(this);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("Heat", buffer.serializeNBT());
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		buffer.deserializeNBT(compound.getCompoundTag("Heat"));
	}
	
	@Override
	public double extractHeat(double maxExtract, boolean simulate) {
		return buffer.extractHeat(maxExtract, simulate);
	}
	
	@Override
	public double receiveHeat(double maxReceive, boolean simulate) {
		return buffer.receiveHeat(maxReceive, simulate);
	}
	
	@Override
	public void increaseHeat(double maxHeat, int heatModifier) {
		buffer.increaseHeat(maxHeat, heatModifier);
	}
	
	@Override
	public void update() {
		tickHelper.onTick();
		if (tickHelper.updateOnInterval(40)) {
			if (lastHeat == buffer.getHeatStored()) {
				buffer.reduceHeat(1);
			}
			lastHeat = buffer.getHeatStored();
		}
	}
	
	@Override
	public void onChangeHeat() {
		container.sendToClient();
	}
	
	@Override
	public void reduceHeat(int heatModifier) {
		buffer.reduceHeat(heatModifier);
	}
	
	@Override
	public void setHeatStored(double heatBuffer) {
		buffer.setHeatStored(heatBuffer);
	}
	
	@Override
	public double getHeatStored() {
		return buffer.getHeatStored();
	}
	
	@Override
	public double getCapacity() {
		return buffer.getCapacity();
	}
	
	@Override
	public HeatLevel getHeatLevel() {
		return buffer.getHeatLevel();
	}
	
	public HeatBuffer getBuffer() {
		return buffer;
	}
	
	@Override
	public void writeData(PacketBuffer data) {
		buffer.writeData(data);
	}
	
	@Override
	public void readData(PacketBuffer data) throws IOException {
		buffer.readData(data);
	}
}
