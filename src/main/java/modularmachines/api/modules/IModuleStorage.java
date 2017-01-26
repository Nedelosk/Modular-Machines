package modularmachines.api.modules;

import java.util.Collection;

public interface IModuleStorage {

	/**
	 * 
	 * @return A collection of all modules that this handler does handle.
	 */
	Collection<Module> getModules();
	
	Module getModule(int index);
	
	IModuleLogic getLogic();
	
}
