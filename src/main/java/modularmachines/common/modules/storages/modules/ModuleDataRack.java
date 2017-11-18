package modularmachines.common.modules.storages.modules;

import modularmachines.api.modules.EnumModulePositions;
import modularmachines.api.modules.ModuleData;

public class ModuleDataRack extends ModuleData {
	
	public ModuleDataRack() {
		super(EnumModulePositions.LEFT, EnumModulePositions.RIGHT);
	}
}
