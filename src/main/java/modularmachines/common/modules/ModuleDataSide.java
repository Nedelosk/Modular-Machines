package modularmachines.common.modules;

import modularmachines.api.modules.EnumModulePositions;
import modularmachines.api.modules.ModuleData;

public class ModuleDataSide extends ModuleData {
	
	public ModuleDataSide() {
		super(EnumModulePositions.LEFT, EnumModulePositions.RIGHT);
	}
	
}
