package de.nedelosk.modularmachines.api.energy;

public class HeatLevel implements IHeatLevel {
	public double heatMin;
	public double heatStepUp;
	public double heatStepDown;

	public HeatLevel(double heatMin, double heatStepUp, double heatStepDown) {
		this.heatMin = heatMin;
		this.heatStepUp = heatStepUp;
		this.heatStepDown = heatStepDown;
	}

	@Override
	public double getHeatMin() {
		return heatMin;
	}

	@Override
	public int getIndex() {
		return EnergyRegistry.getHeatLevelIndex(this);
	}

	@Override
	public double getHeatStepUp() {
		return heatStepUp;
	}

	@Override
	public double getHeatStepDown() {
		return heatStepDown;
	}

	@Override
	public int compareTo(IHeatLevel o) {
		if(o == null){
			return 1;
		}
		if(o.getHeatMin() < heatMin){
			return 1;

		}else if(o.getHeatMin() == heatMin){
			return 0;
		}
		return -1;
	}
}
