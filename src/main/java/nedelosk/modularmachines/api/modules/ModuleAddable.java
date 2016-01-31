package nedelosk.modularmachines.api.modules;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.utils.ModularException;
import nedelosk.modularmachines.api.utils.ModuleStack;

public abstract class ModuleAddable<S extends IModuleSaver> extends ModuleDefault<S> implements IModuleAddable<S> {

	public ModuleAddable(String categoryUID, String moduleUID) {
		super(categoryUID, moduleUID);
	}

	@Override
	public void onAddInModular(IModular modular, ModuleStack stack) throws ModularException {
	}
}
