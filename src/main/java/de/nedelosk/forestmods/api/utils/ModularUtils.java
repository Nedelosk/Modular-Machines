package de.nedelosk.forestmods.api.utils;

import java.util.List;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.managers.IModularModuleManager;
import de.nedelosk.forestmods.api.modules.casing.IModuleCasing;
import de.nedelosk.forestmods.api.modules.engine.IModuleEngine;
import de.nedelosk.forestmods.api.modules.heater.IModuleHeater;
import de.nedelosk.forestmods.api.modules.storage.battery.IModuleBattery;
import de.nedelosk.forestmods.api.modules.storage.capacitors.IModuleCapacitor;
import de.nedelosk.forestmods.api.producers.IModule;
import de.nedelosk.forestmods.api.producers.IModuleAdvanced;

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
		return getModuleContainers(modular, moduleClass).get(0);
	}

	public static List<ModuleStack> getModuleContainers(IModular modular, Class<? extends IModule> moduleClass) {
		if (modular == null) {
			return null;
		}
		return getModuleManager(modular).getModuleSatcks(moduleClass);
	}

	private static IModularModuleManager getModuleManager(IModular modular) {
		return modular.getManager(IModularModuleManager.class);
	}
}
