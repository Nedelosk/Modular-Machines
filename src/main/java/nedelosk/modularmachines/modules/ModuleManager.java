package nedelosk.modularmachines.modules;

import nedelosk.forestcore.library.modules.AModuleManager;

public class ModuleManager extends AModuleManager {

	public static ModuleCore core;
	public static ModuleModular modular;

	@Override
	public void registerModules() {
		registerModule(core = new ModuleCore());
		registerModule(modular = new ModuleModular());
	}

}
