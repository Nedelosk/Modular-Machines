package de.nedelosk.forestmods.api.modules.container;

import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.modules.inventory.IModuleInventory;

public interface ISingleInventoryContainer<M extends IModule, S extends IModuleSaver> extends IInventoryContainer {

	void setInventory(IModuleInventory<M, S> inv);

	IModuleInventory<M, S> getInventory();
}
