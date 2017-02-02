package modularmachines.common.modules.storages.modules;

import modularmachines.api.modules.EnumModuleSizes;
import modularmachines.api.modules.storages.EnumStoragePosition;
import modularmachines.api.modules.storages.IStoragePosition;

public class ModuleDataCasing extends ModuleDataStorageModule {
	
	public ModuleDataCasing(EnumModuleSizes storageSize) {
		super(storageSize);
	}
	
	@Override
	public boolean isPositionValid(IStoragePosition position) {
		return position == EnumStoragePosition.CASING;
	}
	
}
