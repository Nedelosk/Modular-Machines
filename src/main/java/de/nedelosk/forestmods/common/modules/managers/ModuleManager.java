package de.nedelosk.forestmods.common.modules.managers;

import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.modules.gui.IModuleGui;
import de.nedelosk.forestmods.api.modules.managers.IModuleManager;
import de.nedelosk.forestmods.api.utils.ModuleCategoryUIDs;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modules.ModuleAddable;

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
