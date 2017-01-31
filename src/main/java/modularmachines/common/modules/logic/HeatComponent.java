package modularmachines.common.modules.logic;

import modularmachines.api.modules.energy.IHeatSource;
import modularmachines.api.modules.logic.LogicComponent;
import modularmachines.common.energy.HeatBuffer;

public class HeatComponent extends LogicComponent implements IHeatSource{

	public final HeatBuffer buffer;
	
	public HeatComponent() {
		buffer = new HeatBuffer(500, 15F);
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

}
