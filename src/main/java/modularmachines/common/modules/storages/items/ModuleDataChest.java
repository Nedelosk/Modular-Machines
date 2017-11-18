package modularmachines.common.modules.storages.items;

import modularmachines.api.modules.EnumModulePositions;
import modularmachines.api.modules.IModulePosition;
import modularmachines.api.modules.ModuleData;

public class ModuleDataChest extends ModuleData {
	
	public ModuleDataChest(IModulePosition... positions) {
		super(EnumModulePositions.RIGHT, EnumModulePositions.LEFT, EnumModulePositions.FRONT, EnumModulePositions.BACK);
	}
	
}
