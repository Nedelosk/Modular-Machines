package nedelosk.modularmachines.api.modular.basic.container.inventory;

import java.util.Collection;

import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.inventory.IModuleInventory;

public interface IMultiInventoryContainer<M extends IModule, O extends Collection<IModuleInventory<M>>> extends IInventoryContainer {

	void addInventory(IModuleInventory<M> inv);

	void addInventory(int index, IModuleInventory<M> inv);

	void setInventorys(O collection);

	int getIndex(IModuleInventory<M> inv);

	O getInventorys();

	IModuleInventory<M> getInventory(int index);

	IModuleInventory<M> getInventory(String moduleUID);
}
