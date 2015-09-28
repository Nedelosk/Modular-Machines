package nedelosk.modularmachines.common.machines;

import java.util.Vector;

import nedelosk.modularmachines.api.basic.machine.modular.IModular;
import nedelosk.modularmachines.api.basic.machine.module.IModule;
import nedelosk.modularmachines.api.basic.machine.module.IModuleCasing;
import nedelosk.modularmachines.api.basic.machine.module.IModuleEngine;
import nedelosk.modularmachines.api.basic.machine.module.IModuleProducer;
import nedelosk.modularmachines.api.basic.machine.module.IModuleStorage;
import nedelosk.modularmachines.api.basic.machine.module.ModuleStack;
import nedelosk.modularmachines.api.basic.machine.module.energy.IModuleBattery;
import nedelosk.modularmachines.api.basic.machine.module.manager.IModuleEnergyManager;
import nedelosk.modularmachines.api.basic.machine.module.manager.IModuleTankManager;

public class ModularUtils {

	public static IModuleCasing getModuleCasing(IModular modular){
		return getModule(modular, "Casing");
	}
	
	public static ModuleStack<IModuleCasing> getModuleStackCasing(IModular modular){
		return getModuleStack(modular, "Casing", 0);
	}
	
	public static IModuleTankManager getModuleTankManager(IModular modular){
		return getModule(modular, "TankManager");
	}
	
	public static ModuleStack<IModuleTankManager> getModuleStackTankManager(IModular modular){
		return getModuleStack(modular, "TankManager", 0);
	}
	
	public static IModuleEnergyManager getModuleEnergyManager(IModular modular){
		return getModule(modular, "EnergyManager");
	}
	
	public static ModuleStack<IModuleEnergyManager> getModuleStackEnergyManager(IModular modular){
		return getModuleStack(modular, "EnergyManager", 0);
	}
	
	public static IModuleBattery getModuleBattery(IModular modular){
		return getModule(modular, "Battery");
	}
	
	public static ModuleStack<IModuleBattery> getModuleStackBattery(IModular modular){
		return getModuleStack(modular, "Battery", 0);
	}
	
	public static IModuleEngine getModuleEngine(IModular modular){
		return getModule(modular, "Engine");
	}
	
	public static ModuleStack<IModuleEngine> getModuleStackEngine(IModular modular){
		return getModuleStack(modular, "Engine", 0);
	}
	
	public static IModuleProducer getModuleProducer(IModular modular){
		return getModule(modular, "Producer");
	}
	
	public static ModuleStack<IModuleProducer> getModuleStackProducer(IModular modular){
		return getModuleStack(modular, "Producer", 0);
	}
	
	public static IModuleStorage getModuleStorage(IModular modular){
		return getModule(modular, "Storage");
	}
	
	public static ModuleStack<IModuleStorage> getModuleStackStorage(IModular modular){
		return getModuleStack(modular, "Storage", 0);
	}
	
	public static Vector<IModule> getModuleCapacitor(IModular modular){
		return getModules(modular, "Capacitor");
	}
	
	public static <M extends IModule> M getModule(IModular modular, String moduleName){
		return (M) getModuleStack(modular, moduleName, 0).getModule();
	}
	
	public static Vector<IModule> getModules(IModular modular, String moduleName){
		Vector<ModuleStack> v =  getModuleStack(modular, moduleName);
		Vector<IModule> modules = new Vector();
		for(ModuleStack stack : v)
			modules.add(stack.getModule());
		return modules;
	}
	
	public static <M extends IModule> M getModule(IModular modular, String moduleName, int ID){
		return (M) getModuleStack(modular, moduleName, ID).getModule();
	}
	
	public static ModuleStack getModuleStack(IModular modular, String moduleName, int ID){
		return getModuleStack(modular, moduleName).get(ID);
	}
	
	public static Vector<ModuleStack> getModuleStack(IModular modular, String moduleName){
		return modular.getModule(moduleName);
	}
	
}
