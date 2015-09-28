package nedelosk.modularmachines.api.basic.machine.modular;

import java.util.HashMap;
import java.util.Vector;

import nedelosk.modularmachines.api.basic.machine.IModularTileEntity;
import nedelosk.modularmachines.api.basic.machine.manager.IModularUtilsManager;
import nedelosk.modularmachines.api.basic.machine.module.IModuleCasing;
import nedelosk.modularmachines.api.basic.machine.module.ModuleStack;
import nedelosk.modularmachines.api.basic.machine.module.manager.IModuleEnergyManager;
import nedelosk.modularmachines.api.basic.machine.module.manager.IModuleTankManager;
import nedelosk.nedeloskcore.api.INBTTagable;

public interface IModular extends INBTTagable {
	
	IModularUtilsManager getManager();
	
	int getTier();
	
	HashMap<String, Vector<ModuleStack>> getModules();
	
	void setModules(HashMap<String, Vector<ModuleStack>> modules);
	
	IModularTileEntity getMachine();
	
	ModuleStack<IModuleEnergyManager> getEnergyManger();
	
	ModuleStack<IModuleCasing> getCasing();

	ModuleStack<IModuleTankManager> getTankManeger();
	
	void addModule(ModuleStack module);
	
	Vector<ModuleStack> getModule(String moduleName);
	
	ModuleStack getModule(String moduleName, int id);
	
	String getName();
	
}
