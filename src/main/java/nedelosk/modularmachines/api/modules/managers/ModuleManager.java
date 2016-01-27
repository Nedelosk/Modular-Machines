package nedelosk.modularmachines.api.modules.managers;

import nedelosk.modularmachines.api.modules.IModuleGui;
import nedelosk.modularmachines.api.modules.Module;
import nedelosk.modularmachines.api.utils.ModuleStack;

public abstract class ModuleManager<S extends IModuleManagerSaver> extends Module<S> implements IModuleManager<S> {

	public ModuleManager(String modifier) {
		super(modifier);
	}

	@Override
	public abstract IModuleGui getGui(ModuleStack stack);

	@Override
	public S getSaver(ModuleStack stack) {
		return (S) new ModuleManagerSaver();
	}
}
