package de.nedelosk.forestmods.api.modules.container;

import java.util.Collection;

import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.modules.inventory.IModuleInventory;

public interface IMultiInventoryContainer<M extends IModule, S extends IModuleSaver, O extends Collection<IModuleInventory<M, S>>> extends IInventoryContainer {

	void addInventory(IModuleInventory<M, S> inv);

	void addInventory(int index, IModuleInventory<M, S> inv);

	void setInventorys(O collection);

	int getIndex(IModuleInventory<M, S> inv);

	O getInventorys();

	IModuleInventory<M, S> getInventory(int index);

	IModuleInventory<M, S> getInventory(String moduleUID);
}
