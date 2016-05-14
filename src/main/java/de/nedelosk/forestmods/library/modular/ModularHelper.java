package de.nedelosk.forestmods.library.modular;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.modules.IModuleMachine;
import de.nedelosk.forestmods.library.modules.ModuleUID;
import de.nedelosk.forestmods.library.modules.casing.IModuleCasing;
import de.nedelosk.forestmods.library.modules.engine.IModuleEngine;
import de.nedelosk.forestmods.library.modules.heater.IModuleHeater;
import de.nedelosk.forestmods.library.modules.storage.IModuleBattery;
import de.nedelosk.forestmods.library.modules.storage.IModuleCapacitor;

public class ModularHelper {

	public static IModuleCasing getCasing(IModular modular) {
		return getModule(modular, IModuleCasing.class);
	}

	public static IModuleBattery getBattery(IModular modular) {
		return getModule(modular, IModuleBattery.class);
	}

	public static IModuleEngine getEngine(IModular modular) {
		return getModule(modular, IModuleEngine.class);
	}

	public static List<IModuleEngine> getEnginesForMachine(IModular modular, IModuleMachine machine) {
		if (modular == null) {
			return null;
		}
		List<IModuleEngine> engines = modular.getModules(IModuleEngine.class);
		if (engines == null || engines.size() == 0) {
			return null;
		}
		ModuleUID machineUID = machine.getModuleContainer().getUID();
		List<IModuleEngine> enginesForMachine = new ArrayList();
		for(IModuleEngine engine : engines){
			if(engine != null && engine.getMachineIndexes() != null && !engine.getMachineIndexes().isEmpty()){
				for(ModuleUID UID : engine.getMachineIndexes()){
					if(UID.equals(machineUID)){
						enginesForMachine.add(engine);
					}
				}
			}
		}
		return enginesForMachine;
	}

	public static IModuleHeater getHeater(IModular modular) {
		return getModule(modular, IModuleHeater.class);
	}

	public static IModuleMachine getMachine(IModular modular) {
		return getModule(modular, IModuleMachine.class);
	}

	public static IModuleCapacitor getCapacitor(IModular modular) {
		return getModule(modular, IModuleCapacitor.class);
	}

	private static <M extends IModule> M getModule(IModular modular, Class<? extends M> moduleClass) {
		if (modular == null) {
			return null;
		}
		List<M> modules = (List<M>) modular.getModules(moduleClass);
		if (modules == null || modules.size() == 0) {
			return null;
		}
		return modules.get(0);
	}
}
