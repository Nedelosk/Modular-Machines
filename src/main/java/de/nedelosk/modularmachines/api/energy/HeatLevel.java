package de.nedelosk.modularmachines.api.energy;

public class HeatLevel implements Comparable<HeatLevel> {
	protected final double heatMin;
	protected final double heatStepUp;
	protected final double heatStepDown;

	public HeatLevel(double heatMin, double heatStepUp, double heatStepDown) {
		this.heatMin = heatMin;
		this.heatStepUp = heatStepUp;
		this.heatStepDown = heatStepDown;
	}

	public double getHeatMin() {
		return heatMin;
	}

	public int getIndex() {
		return EnergyRegistry.getHeatLevelIndex(this);
	}

	public double getHeatStepUp() {
		return heatStepUp;
	}

	public double getHeatStepDown() {
		return heatStepDown;
	}

	@Override
	public int compareTo(HeatLevel o) {
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
