package de.nedelosk.forestmods.api.utils;

import java.util.List;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.modules.IModuleAdvanced;
import de.nedelosk.forestmods.api.modules.casing.IModuleCasing;
import de.nedelosk.forestmods.api.modules.engine.IModuleEngine;
import de.nedelosk.forestmods.api.modules.heater.IModuleHeater;
import de.nedelosk.forestmods.api.modules.storage.IModuleBattery;
import de.nedelosk.forestmods.api.modules.storage.IModuleCapacitor;

public class ModularUtils {

	public static ModuleStack<IModuleCasing> getCasing(IModular modular) {
		return getModuleContainer(modular, IModuleCasing.class);
	}

	public static ModuleStack<IModuleBattery> getBattery(IModular modular) {
		return getModuleContainer(modular, IModuleBattery.class);
	}

	public static ModuleStack<IModuleEngine> getEngine(IModular modular) {
		return getModuleContainer(modular, IModuleEngine.class);
	}

	public static ModuleStack<IModuleHeater> getHeater(IModular modular) {
		return getModuleContainer(modular, IModuleHeater.class);
	}

	public static <M extends IModuleAdvanced> ModuleStack<M> getMachine(IModular modular) {
		return getModuleContainer(modular, IModuleAdvanced.class);
	}

	public static ModuleStack<IModuleCapacitor> getCapacitor(IModular modular) {
		return getModuleContainer(modular, IModuleCapacitor.class);
	}

	public static ModuleStack getModuleContainer(IModular modular, Class<? extends IModule> moduleClass) {
		if (modular == null) {
			return null;
		}
		List<ModuleStack> stacks = getModuleStacks(modular, moduleClass);
		if (stacks == null || stacks.size() == 0) {
			return null;
		}
		return stacks.get(0);
	}

	public static List<ModuleStack> getModuleStacks(IModular modular, Class<? extends IModule> moduleClass) {
		if (modular == null) {
			return null;
		}
		return modular.getModuleSatcks(moduleClass);
	}
}
