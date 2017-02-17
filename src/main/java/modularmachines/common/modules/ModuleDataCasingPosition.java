package modularmachines.common.modules;

import modularmachines.api.modules.ModuleData;
import modularmachines.api.modules.storages.EnumStoragePosition;
import modularmachines.api.modules.storages.IStoragePosition;

public class ModuleDataCasingPosition extends ModuleData {
	
	@Override
	public boolean isPositionValid(IStoragePosition position) {
		return position == EnumStoragePosition.CASING;
	}
	
}
