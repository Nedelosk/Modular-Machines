package de.nedelosk.modularmachines.api.energy;

public interface IHeatSource {

	double extractHeat(double maxExtract, boolean simulate);

	double receiveHeat(double maxReceive, boolean simulate);

	void increaseHeat(int heatModifier);

	void reduceHeat(int heatModifier);

	IHeatLevel getHeatLevel();

	void setHeatStored(double heatBuffer);

	double getHeatStored();

	double getCapacity();

}
