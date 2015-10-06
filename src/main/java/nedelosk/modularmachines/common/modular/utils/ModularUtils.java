package nedelosk.modularmachines.common.modular.utils;

import java.util.Vector;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.basic.basic.IModuleCasing;
import nedelosk.modularmachines.api.modular.module.basic.energy.IModuleBattery;
import nedelosk.modularmachines.api.modular.module.basic.energy.IModuleEngine;
import nedelosk.modularmachines.api.modular.module.basic.fluids.IModuleFluidManager;
import nedelosk.modularmachines.api.modular.module.basic.storage.IModuleStorage;
import nedelosk.modularmachines.api.modular.module.producer.producer.IModuleProducer;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;

public class ModularUtils {

	public static IModuleCasing getModuleCasing(IModular modular){
		return getModule(modular, "Casing");
	}
	
	public static ModuleStack<IModuleCasing> getModuleStackCasing(IModular modular){
		return getModuleStack(modular, "Casing", 0);
	}
	
	public static IModuleFluidManager getModuleTankManager(IModular modular){
		return getModule(modular, "TankManager");
	}
	
	public static ModuleStack<IModuleFluidManager> getModuleStackTankManager(IModular modular){
		return getModuleStack(modular, "TankManager", 0);
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
