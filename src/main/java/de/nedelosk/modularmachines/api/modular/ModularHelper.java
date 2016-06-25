package de.nedelosk.modularmachines.api.modular;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.casing.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.engine.IModuleEngine;
import de.nedelosk.modularmachines.api.modules.heater.IModuleHeater;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IModuleBattery;
import de.nedelosk.modularmachines.api.modules.storage.IModuleCapacitor;
import de.nedelosk.modularmachines.api.modules.tool.IModuleTool;

public class ModularHelper {

	public static IModuleState<IModuleCasing> getCasing(IModular modular) {
		return getModule(modular, IModuleCasing.class);
	}

	public static IModuleState<IModuleBattery> getBattery(IModular modular) {
		return getModule(modular, IModuleBattery.class);
	}

	public static IModuleState<IModuleEngine> getEngine(IModular modular) {
		return getModule(modular, IModuleEngine.class);
	}

	public static List<IModuleState<IModuleEngine>> getEnginesForTool(IModuleState<IModuleTool> tool) {
		IModular modular = tool.getModular();

		if (modular == null) {
			return null;
		}
		List<IModuleState<IModuleEngine>> engines = modular.getModules(IModuleEngine.class);
		if (engines == null || engines.size() == 0) {
			return null;
		}
		int machineIndex = tool.getIndex();
		List<IModuleState<IModuleEngine>> enginesForMachine = new ArrayList();
		for(IModuleState<IModuleEngine> engine : engines){
			if(engine != null && engine.getModule().getMachineIndexes(engine) != null && !engine.getModule().getMachineIndexes(engine).isEmpty()){
				for(int machineIndexs : engine.getModule().getMachineIndexes(engine)){
					if(machineIndexs == machineIndex){
						enginesForMachine.add(engine);
					}
				}
			}
		}
		return enginesForMachine;
	}

	public static IModuleState<IModuleHeater> getHeater(IModular modular) {
		return getModule(modular, IModuleHeater.class);
	}

	public static IModuleState<IModuleTool> getMachine(IModular modular) {
		return getModule(modular, IModuleTool.class);
	}

	public static IModuleState<IModuleCapacitor> getCapacitor(IModular modular) {
		return getModule(modular, IModuleCapacitor.class);
	}

	private static <M extends IModule> IModuleState<M> getModule(IModular modular, Class<? extends M> moduleClass) {
		if (modular == null) {
			return null;
		}
		List<IModuleState<M>> modules = modular.getModules(moduleClass);
		if (modules == null || modules.size() == 0) {
			return null;
		}
		return modules.get(0);
	}
}
