package modularmachines.common.modules.storages.modules;

import modularmachines.api.modules.EnumModuleSizes;
import modularmachines.api.modules.storages.EnumStoragePosition;
import modularmachines.api.modules.storages.IStoragePosition;

public class ModuleDataRack extends ModuleDataStorageModule {

	public ModuleDataRack(EnumModuleSizes storageSize) {
		super(storageSize);
	}
	
	@Override
	public boolean isPositionValid(IStoragePosition position) {
		return false;
	}
	
	@Override
	public boolean isStorage(IStoragePosition position) {
		return position == EnumStoragePosition.LEFT || position == EnumStoragePosition.RIGHT;
	}

}
