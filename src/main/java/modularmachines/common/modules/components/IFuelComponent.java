package modularmachines.common.modules.components;

import modularmachines.api.modules.components.IModuleComponent;

public interface IFuelComponent extends IModuleComponent {
	int getFuel();
	
	int getFuelTotal();
	
	boolean hasFuel();
	
	void removeFuel();
	
	boolean updateFuel();
}
