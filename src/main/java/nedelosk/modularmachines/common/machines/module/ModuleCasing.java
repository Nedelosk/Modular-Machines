package nedelosk.modularmachines.common.machines.module;

import nedelosk.modularmachines.api.basic.machine.module.IModuleCasing;
import nedelosk.modularmachines.api.basic.machine.module.Module;

public class ModuleCasing extends Module implements IModuleCasing {

	@Override
	public String getModuleName() {
		return "Casing";
	}

}
