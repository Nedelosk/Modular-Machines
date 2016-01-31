package nedelosk.modularmachines.api.modules.managers;

import nedelosk.modularmachines.api.modules.ModuleAddable;
import nedelosk.modularmachines.api.modules.gui.IModuleGui;
import nedelosk.modularmachines.api.utils.ModuleCategoryUIDs;
import nedelosk.modularmachines.api.utils.ModuleStack;

public abstract class ModuleManager<S extends IModuleManagerSaver> extends ModuleAddable<S> implements IModuleManager<S> {

	public ModuleManager(String moduleUID) {
		super(ModuleCategoryUIDs.MANAGERS, moduleUID);
	}

	@Override
	public abstract IModuleGui createGui(ModuleStack stack);

	@Override
	public S createSaver(ModuleStack stack) {
		return (S) new ModuleManagerSaver();
	}
}
