package modularmachines.api.modules.energy;

public interface IHeatStep extends Comparable<IHeatStep> {
	
	double getHeatMin();
	
	int getIndex();
	
	double getHeatStepUp();
	
	double getHeatStepDown();
}
