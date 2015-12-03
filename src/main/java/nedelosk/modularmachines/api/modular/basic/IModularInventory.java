package nedelosk.modularmachines.api.modular.basic;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.basic.managers.IModularInventoryManager;

public interface IModularInventory extends IModular {

	IModularInventoryManager getInventoryManager();

}
