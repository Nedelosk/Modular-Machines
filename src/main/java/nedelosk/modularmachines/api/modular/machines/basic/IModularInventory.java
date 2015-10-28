package nedelosk.modularmachines.api.modular.machines.basic;

import nedelosk.modularmachines.api.modular.machines.manager.IModularInventoryManager;

public interface IModularInventory extends IModular {

	IModularInventoryManager getInventoryManager();

}
