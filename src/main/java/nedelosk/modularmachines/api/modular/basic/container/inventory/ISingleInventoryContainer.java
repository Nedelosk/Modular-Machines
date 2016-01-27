package nedelosk.modularmachines.api.modular.basic.container.inventory;

import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.inventory.IModuleInventory;

public interface ISingleInventoryContainer<M extends IModule> extends IInventoryContainer {

	void setInventory(IModuleInventory<M> inv);

	IModuleInventory<M> getInventory();
}
