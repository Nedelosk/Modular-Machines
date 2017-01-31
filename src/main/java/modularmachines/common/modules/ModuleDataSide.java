package modularmachines.common.modules;

import modularmachines.api.modules.ModuleData;
import modularmachines.api.modules.storages.EnumStoragePosition;
import modularmachines.api.modules.storages.IStoragePosition;

public class ModuleDataSide extends ModuleData {
	
	@Override
	public boolean isPositionValid(IStoragePosition position) {
		return position == EnumStoragePosition.LEFT || position == EnumStoragePosition.RIGHT;
	}
	
}
