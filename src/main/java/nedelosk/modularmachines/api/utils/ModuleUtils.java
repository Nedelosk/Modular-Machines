package nedelosk.modularmachines.api.utils;

import java.util.Collection;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.basic.container.module.IModuleContainer;
import nedelosk.modularmachines.api.modular.basic.container.module.IMultiModuleContainer;
import nedelosk.modularmachines.api.modular.basic.container.module.ISingleModuleContainer;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.managers.fluids.IModuleTankManager;

public class ModuleUtils {

	public static ISingleModuleContainer getCasing(IModular modular) {
		return getSingleContainer(modular, ModuleCategoryUIDs.CASING);
	}

	public static ModuleStack<IModuleTankManager> getTankManager(IModular modular) {
		return getModuleStack(modular, ModuleCategoryUIDs.MANAGERS, ModuleCategoryUIDs.MANAGER_TANK);
	}

	public static ISingleModuleContainer getBattery(IModular modular) {
		return getSingleContainer(modular, ModuleCategoryUIDs.BATTERY);
	}

	public static ISingleModuleContainer getEngine(IModular modular) {
		return getSingleContainer(modular, ModuleCategoryUIDs.ENGINE);
	}

	public static ISingleModuleContainer getMachine(IModular modular) {
		return getSingleContainer(modular, ModuleCategoryUIDs.MACHINE);
	}

	public static IMultiModuleContainer<IModule, Collection<ModuleStack<IModule>>> getStorages(IModular modular) {
		return getMultiContainer(modular, ModuleCategoryUIDs.STORAGES);
	}

	public static IMultiModuleContainer<IModule, Collection<ModuleStack<IModule>>> getCapacitors(IModular modular) {
		return getMultiContainer(modular, ModuleCategoryUIDs.CAPACITOR);
	}

	public static IMultiModuleContainer<IModule, Collection<ModuleStack<IModule>>> getMultiContainer(IModular modular, String categoryUID) {
		IModuleContainer container = getModuleStack(modular, categoryUID);
		if (container == null || !(container instanceof IMultiModuleContainer)) {
			return null;
		}
		return (IMultiModuleContainer) container;
	}

	public static ModuleStack getModuleStack(IModular modular, String categoryUID, String moduleUID) {
		IMultiModuleContainer container = getMultiContainer(modular, categoryUID);
		if (container == null) {
			return null;
		}
		return container.getStack(moduleUID);
	}

	public static ISingleModuleContainer getSingleContainer(IModular modular, String categoryUID) {
		IModuleContainer container = getModuleStack(modular, categoryUID);
		if (container == null || !(container instanceof ISingleModuleContainer)) {
			return null;
		}
		return (ISingleModuleContainer) container;
	}

	public static IModuleContainer getModuleStack(IModular modular, String categoryUID) {
		if (modular == null) {
			return null;
		}
		return modular.getModule(categoryUID);
	}
}
