package modularmachines.common.energy;

import modularmachines.api.modules.energy.IHeatStep;

public class HeatStep implements IHeatStep {
	protected final double heatMin;
	protected final double heatStepUp;
	protected final double heatStepDown;
	protected int index = -1;
	
	public HeatStep(double heatMin, double heatStepUp, double heatStepDown) {
		this.heatMin = heatMin;
		this.heatStepUp = heatStepUp;
		this.heatStepDown = heatStepDown;
	}
	
	public double getHeatMin() {
		return heatMin;
	}
	
	public void setIndex(int index) {
		if (index >= 0) {
			this.index = index;
		}
	}
	
	public int getIndex() {
		return index;
	}
	
	public double getHeatStepUp() {
		return heatStepUp;
	}
	
	public double getHeatStepDown() {
		return heatStepDown;
	}
	
	@Override
	public int compareTo(IHeatStep o) {
		if (o == null) {
			return 1;
		}
		if (o.getHeatMin() < heatMin) {
			return 1;
		} else if (o.getHeatMin() == heatMin) {
			return 0;
		}
		return -1;
	}
}
