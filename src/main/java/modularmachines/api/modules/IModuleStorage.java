package modularmachines.api.modules;

import java.util.List;

import modularmachines.api.modules.logic.IModuleLogic;

public interface IModuleStorage {

	/**
	 * 
	 * @return A collection of all modules that this handler does handle.
	 */
	List<Module> getModules();
	
	Module getModuleForIndex(int index);
	
	Module getModuleAtPosition(int position);
	
	int getPosition(Module module);
	
	IModuleLogic getLogic();
	
}