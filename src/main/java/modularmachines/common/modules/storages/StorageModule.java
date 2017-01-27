package modularmachines.common.modules.storages;

import modularmachines.api.modules.EnumModuleSizes;
import modularmachines.api.modules.storages.IStoragePosition;

public class StorageModule extends Storage {

	protected final EnumModuleSizes size;
	
	public StorageModule(IStoragePosition position, ModuleStorage storage, EnumModuleSizes size) {
		super(position, storage);
		this.size = size;
	}

	public EnumModuleSizes getSize() {
		return size;
	}
}
