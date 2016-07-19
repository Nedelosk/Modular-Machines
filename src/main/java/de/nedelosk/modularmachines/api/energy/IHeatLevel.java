package de.nedelosk.modularmachines.api.energy;


public interface IHeatLevel extends Comparable<IHeatLevel> {

	int getIndex();

	double getHeatMin();

	double getHeatStepUp();

	double getHeatStepDown();
}
