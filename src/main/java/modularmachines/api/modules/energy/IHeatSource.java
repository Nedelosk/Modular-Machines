package modularmachines.api.modules.energy;

public interface IHeatSource {
	
	double extractHeat(double maxExtract, boolean simulate);
	
	double receiveHeat(double maxReceive, boolean simulate);
	
	void increaseHeat(double maxHeat, int heatModifier);
	
	void reduceHeat(int heatModifier);
	
	void setHeatStored(double heatBuffer);
	
	double getHeatStored();
	
	double getCapacity();
	
	HeatLevel getHeatLevel();
}
