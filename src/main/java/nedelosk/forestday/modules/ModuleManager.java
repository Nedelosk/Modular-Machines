package nedelosk.forestday.modules;

import nedelosk.forestcore.library.modules.AModuleManager;

public class ModuleManager extends AModuleManager {

	public static ModuleCore core;
	public static ModuleCoal coal;

	@Override
	public void registerModules() {
		registerModule(core = new ModuleCore());
		registerModule(coal = new ModuleCoal());
	}
}
