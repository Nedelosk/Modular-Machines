package modularmachines.common.modules.logic;

import modularmachines.api.modules.energy.IHeatSource;
import modularmachines.api.modules.logic.LogicComponent;
import modularmachines.common.energy.HeatBuffer;
import net.minecraft.nbt.NBTTagCompound;

public class HeatComponent extends LogicComponent implements IHeatSource{

	protected final HeatBuffer buffer;
	
	public HeatComponent() {
		buffer = new HeatBuffer(500, 15F);
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
	
	public HeatBuffer getBuffer() {
		return buffer;
	}

}
