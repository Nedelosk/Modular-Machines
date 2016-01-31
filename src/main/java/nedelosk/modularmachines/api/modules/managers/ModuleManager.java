package nedelosk.modularmachines.api.modules.managers;

import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.modules.ModuleAddable;
import nedelosk.modularmachines.api.modules.gui.IModuleGui;
import nedelosk.modularmachines.api.utils.ModuleCategoryUIDs;
import nedelosk.modularmachines.api.utils.ModuleStack;

public abstract class ModuleManager extends ModuleAddable implements IModuleManager {

	public ModuleManager(String moduleUID) {
		super(ModuleCategoryUIDs.MANAGERS, moduleUID);
	}

	@Override
	public abstract IModuleGui createGui(ModuleStack stack);

	@Override
	public IModuleSaver createSaver(ModuleStack stack) {
		return new ModuleManagerSaver();
	}
}
