package nedelosk.modularmachines.common.modular.module.basic.storage;

import nedelosk.modularmachines.api.modular.module.basic.gui.ModuleGui;
import nedelosk.modularmachines.api.modular.module.basic.storage.IModuleStorageManager;

public class ModuleStorageManager extends ModuleGui implements IModuleStorageManager {

	public ModuleStorageManager() {
	}

	@Override
	public String getModuleName() {
		return "StorageManager";
	}

}
