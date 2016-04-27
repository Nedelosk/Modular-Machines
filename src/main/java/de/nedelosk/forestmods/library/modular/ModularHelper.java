package de.nedelosk.forestmods.library.modular;

import java.util.List;

import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.modules.IModuleMachine;
import de.nedelosk.forestmods.library.modules.casing.IModuleCasing;
import de.nedelosk.forestmods.library.modules.engine.IModuleEngine;
import de.nedelosk.forestmods.library.modules.heater.IModuleHeater;
import de.nedelosk.forestmods.library.modules.storage.IModuleBattery;
import de.nedelosk.forestmods.library.modules.storage.IModuleCapacitor;

public class ModularHelper {

	public static IModuleCasing getCasing(IModular modular) {
		return getModuleContainer(modular, IModuleCasing.class);
	}

	public static IModuleBattery getBattery(IModular modular) {
		return getModuleContainer(modular, IModuleBattery.class);
	}

	public static IModuleEngine getEngine(IModular modular) {
		return getModuleContainer(modular, IModuleEngine.class);
	}

	public static IModuleHeater getHeater(IModular modular) {
		return getModuleContainer(modular, IModuleHeater.class);
	}

	public static IModuleMachine getMachine(IModular modular) {
		return getModuleContainer(modular, IModuleMachine.class);
	}

	public static IModuleCapacitor getCapacitor(IModular modular) {
		return getModuleContainer(modular, IModuleCapacitor.class);
	}

	private static <M extends IModule> M getModuleContainer(IModular modular, Class<? extends M> moduleClass) {
		if (modular == null) {
			return null;
		}
		List<M> stacks = (List<M>) modular.getModules(moduleClass);
		if (stacks == null || stacks.size() == 0) {
			return null;
		}
		return stacks.get(0);
	}
}
