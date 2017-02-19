package modularmachines.common.modules.storages.items;

import modularmachines.api.modules.storages.EnumStoragePosition;
import modularmachines.api.modules.storages.IStoragePosition;
import modularmachines.common.modules.storages.ModuleDataStorage;

public class ModuleDataChest extends ModuleDataStorage {
	
	@Override
	public boolean isStorage(IStoragePosition position) {
		return position == EnumStoragePosition.LEFT || position == EnumStoragePosition.RIGHT || position == EnumStoragePosition.BACK;
	}
	
}
