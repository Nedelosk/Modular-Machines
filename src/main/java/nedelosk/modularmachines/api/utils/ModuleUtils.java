package nedelosk.modularmachines.api.utils;

import java.util.Vector;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.casing.IModuleCasing;
import nedelosk.modularmachines.api.producers.IProducer;
import nedelosk.modularmachines.api.producers.energy.IProducerBattery;
import nedelosk.modularmachines.api.producers.engine.IProducerEngine;
import nedelosk.modularmachines.api.producers.machines.IProducerMachine;
import nedelosk.modularmachines.api.producers.managers.fluids.IProducerTankManager;
import nedelosk.modularmachines.api.producers.storage.IProducerStorage;

public class ModuleUtils {

	public static ModuleStack<IModuleCasing, IProducer> getModuleStackCasing(IModular modular) {
		return getModuleStack(modular, "Casing", 0);
	}

	public static ModuleStack<IModule, IProducerTankManager> getModuleStackTankManager(IModular modular) {
		return getModuleStack(modular, "TankManager", 0);
	}

	public static ModuleStack<IModule, IProducerBattery> getModuleStackBattery(IModular modular) {
		return getModuleStack(modular, "Battery", 0);
	}

	public static ModuleStack<IModule, IProducerEngine> getModuleStackEngine(IModular modular) {
		return getModuleStack(modular, "Engine", 0);
	}

	public static ModuleStack<IModule, IProducerMachine> getModuleStackMachine(IModular modular) {
		return getModuleStack(modular, "Machine", 0);
	}

	public static IProducerStorage getModuleStorage(IModular modular) {
		return getModule(modular, "Storage");
	}

	public static ModuleStack<IModule, IProducerStorage> getModuleStackStorage(IModular modular) {
		return getModuleStack(modular, "Storage", 0);
	}

	public static Vector<ModuleStack> getModuleStackCapacitors(IModular modular) {
		return getModuleStacks(modular, "Capacitor");
	}

	public static <M extends IModule> M getModule(IModular modular, String moduleName) {
		if (getModuleStack(modular, moduleName, 0) == null)
			return null;
		return (M) getModuleStack(modular, moduleName, 0).getModule();
	}

	public static Vector<ModuleStack> getModuleStacks(IModular modular, String moduleName) {
		Vector<ModuleStack> v = getModuleStack(modular, moduleName);
		return v;
	}

	public static <M extends IModule> M getModule(IModular modular, String moduleName, int ID) {
		if (getModuleStack(modular, moduleName, ID) == null)
			return null;
		return (M) getModuleStack(modular, moduleName, ID).getModule();
	}

	public static ModuleStack getModuleStack(IModular modular, String moduleName, int ID) {
		if (getModuleStack(modular, moduleName) == null)
			return null;
		return getModuleStack(modular, moduleName).get(ID);
	}

	public static Vector<ModuleStack> getModuleStack(IModular modular, String moduleName) {
		if(modular == null)
			return null;
		return modular.getModule(moduleName);
	}

}
