package de.nedelosk.forestmods.common.core.modules;

import de.nedelosk.forestcore.modules.AModuleManager;

public class ModuleManager extends AModuleManager {

	@Override
	public void registerModules() {
		registerModule(new ModuleCore());
		registerModule(new ModuleForestDay());
		registerModule(new ModuleModularMachine());
	}
}
