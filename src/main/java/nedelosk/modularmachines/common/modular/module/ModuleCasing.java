package nedelosk.modularmachines.common.modular.module;

import java.util.ArrayList;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.module.IModuleCasing;
import nedelosk.modularmachines.api.modular.module.Module;
import nedelosk.modularmachines.api.modular.module.ModuleStack;

public class ModuleCasing extends Module implements IModuleCasing {

	@Override
	public String getModuleName() {
		return "Casing";
	}

}
