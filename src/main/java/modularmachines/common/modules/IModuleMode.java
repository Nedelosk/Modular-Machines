package modularmachines.common.modules;

import modularmachines.api.recipes.IMode;

public interface IModuleMode {

	IMode getCurrentMode();
	
	void setCurrentMode(int ordinal);
	
	IMode getDefaultMode();
	
}
