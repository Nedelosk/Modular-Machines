package nedelosk.modularmachines.api.modular.basic.container.inventory;

import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.modules.inventory.IModuleInventory;

public interface ISingleInventoryContainer<M extends IModule, S extends IModuleSaver> extends IInventoryContainer {

	void setInventory(IModuleInventory<M, S> inv);

	IModuleInventory<M, S> getInventory();
}
