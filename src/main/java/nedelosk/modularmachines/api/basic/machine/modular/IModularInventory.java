package nedelosk.modularmachines.api.basic.machine.modular;

import nedelosk.modularmachines.api.basic.machine.manager.IModularInventoryManager;

public interface IModularInventory extends IModular {

	void update();
	
	IModularInventoryManager getInventoryManager();
	
}
