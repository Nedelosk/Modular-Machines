package nedelosk.modularmachines.api.modular.utils;

import java.util.Vector;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.basic.basic.IModuleCasing;
import nedelosk.modularmachines.api.modular.module.tool.producer.IProducer;
import nedelosk.modularmachines.api.modular.module.tool.producer.energy.IProducerBattery;
import nedelosk.modularmachines.api.modular.module.tool.producer.energy.IProducerEngine;
import nedelosk.modularmachines.api.modular.module.tool.producer.fluids.IProducerFluidManager;
import nedelosk.modularmachines.api.modular.module.tool.producer.machine.IProducerMachine;
import nedelosk.modularmachines.api.modular.module.tool.producer.storage.IProducerStorage;

public class ModularUtils {
	
	public static ModuleStack<IModuleCasing, IProducer> getModuleStackCasing(IModular modular){
		return getModuleStack(modular, "Casing", 0);
	}
	
	public static ModuleStack<IModule, IProducerFluidManager> getModuleStackTankManager(IModular modular){
		return getModuleStack(modular, "TankManager", 0);
	}
	
	public static ModuleStack<IModule, IProducerBattery> getModuleStackBattery(IModular modular){
		return getModuleStack(modular, "Battery", 0);
	}
	
	public static ModuleStack<IModule, IProducerEngine> getModuleStackEngine(IModular modular){
		return getModuleStack(modular, "Engine", 0);
	}
	
	public static ModuleStack<IModule, IProducerMachine> getModuleStackMachine(IModular modular){
		return getModuleStack(modular, "Machine", 0);
	}
	
	public static IProducerStorage getModuleStorage(IModular modular){
		return getModule(modular, "Storage");
	}
	
	public static ModuleStack<IModule, IProducerStorage> getModuleStackStorage(IModular modular){
		return getModuleStack(modular, "Storage", 0);
	}
	
	public static Vector<IModule> getModuleCapacitor(IModular modular){
		return getModules(modular, "Capacitor");
	}
	
	public static <M extends IModule> M getModule(IModular modular, String moduleName){
		if(getModuleStack(modular, moduleName, 0) == null)
			return null;
		return (M) getModuleStack(modular, moduleName, 0).getModule();
	}
	
	public static Vector<IModule> getModules(IModular modular, String moduleName){
		Vector<ModuleStack> v =  getModuleStack(modular, moduleName);
		if(v == null)
			return null;
		Vector<IModule> modules = new Vector();
		for(ModuleStack stack : v)
			if(stack != null)
				modules.add(stack.getModule());
		return modules;
	}
	
	public static <M extends IModule> M getModule(IModular modular, String moduleName, int ID){
		if(getModuleStack(modular, moduleName, ID) == null)
			return null;
		return (M) getModuleStack(modular, moduleName, ID).getModule();
	}
	
	public static ModuleStack getModuleStack(IModular modular, String moduleName, int ID){
		if(getModuleStack(modular, moduleName) == null)
			return null;
		return getModuleStack(modular, moduleName).get(ID);
	}
	
	public static Vector<ModuleStack> getModuleStack(IModular modular, String moduleName){
		return modular.getModule(moduleName);
	}
	
}
