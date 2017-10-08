package modularmachines.api.modules;

import java.util.List;

import modularmachines.api.modules.logic.IModuleLogic;

public interface IModuleStorage {

	/**
	 * @return a collection with all modules of this storage.
	 */
	List<Module> getModules();
	
	Module getModule(int index);
	
	/**
	 * @return the module that is at this position of the collection.
	 */
	Module getModuleAtPosition(int position);
	
	/**
	 * @return the position of this module át the collection.
	 */
	int getPosition(Module module);
	
	/**
	 * @return the module logic of this storage.
	 */
	IModuleLogic getLogic();
	
}
