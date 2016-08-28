package de.nedelosk.modularmachines.api.energy;

public interface IKineticSource {

	double extractKineticEnergy(double maxExtract, boolean simulate);

	double receiveKineticEnergy(double maxReceive, boolean simulate);

	void increaseKineticEnergy(double kineticModifier);

	void reduceKineticEnergy(double kineticModifier);

	double getStored();

	double getCapacity();

}
