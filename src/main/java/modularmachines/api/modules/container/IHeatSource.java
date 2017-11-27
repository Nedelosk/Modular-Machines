package modularmachines.api.modules.container;

import java.util.Collection;

import modularmachines.api.modules.components.IFirebox;

public interface IHeatSource {
	
	void increaseHeat(int heatModifier);
	
	void reduceHeat(int heatModifier);
	
	void setHeatStored(double heatBuffer);
	
	double getHeat();
	
	double getMaxHeat();
	
	double getHeatLevel();
	
	Collection<IFirebox> getFireboxes();
}
