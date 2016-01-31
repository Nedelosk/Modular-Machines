package nedelosk.modularmachines.api.modular.basic.container.inventory;

import java.util.ArrayList;
import java.util.List;

import nedelosk.modularmachines.api.modular.basic.IModularInventory;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleDefault;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.modules.inventory.IModuleInventory;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class MultiInventoryContainer<M extends IModule, S extends IModuleSaver> implements IMultiInventoryContainer<M, S, List<IModuleInventory<M, S>>> {

	private List<IModuleInventory<M, S>> inventorys = new ArrayList();

	public MultiInventoryContainer(IModuleInventory<M, S> inventory) {
		inventorys.add(inventory);
	}

	public MultiInventoryContainer() {
	}

	@Override
	public void addInventory(int index, IModuleInventory<M, S> stack) {
		inventorys.add(index, stack);
	}

	@Override
	public void addInventory(IModuleInventory<M, S> inv) {
		inventorys.add(inv);
	}

	@Override
	public void setInventorys(List<IModuleInventory<M, S>> collection) {
		this.inventorys = collection;
	}

	@Override
	public IModuleInventory<M, S> getInventory(int index) {
		return inventorys.get(index);
	}

	@Override
	public IModuleInventory<M, S> getInventory(String moduleUID) {
		for ( IModuleInventory<M, S> inv : inventorys ) {
			if (inv.getModuleUID().equals(moduleUID)) {
				return inv;
			}
		}
		return null;
	}

	@Override
	public int getIndex(IModuleInventory<M, S> inv) {
		if (inv == null) {
			return -1;
		}
		return inventorys.indexOf(inv);
	}

	@Override
	public List<IModuleInventory<M, S>> getInventorys() {
		return inventorys;
	}

	/* NBT */
	@Override
	public void readFromNBT(NBTTagCompound nbt, IModularInventory modular, ModuleStack stack) throws Exception {
		if (nbt.hasKey("Inv")) {
			NBTTagList nbtList = nbt.getTagList("Inv", 10);
			inventorys = new ArrayList<IModuleInventory<M, S>>();
			for ( int i = 0; i < nbtList.tagCount(); i++ ) {
				NBTTagCompound nbtTag = nbtList.getCompoundTagAt(i);
				IModuleInventory inv = ((IModuleDefault) stack.getModule()).createInventory(stack);
				inv.readFromNBT(nbtTag, modular, stack);
				inventorys.add(inv);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModularInventory modular, ModuleStack stack) throws Exception {
		if (inventorys != null && inventorys.isEmpty()) {
			NBTTagList nbtList = new NBTTagList();
			for ( IModuleInventory<M, S> inv : inventorys ) {
				if (inv != null) {
					NBTTagCompound nbtTag = new NBTTagCompound();
					inv.writeToNBT(nbtTag, modular, stack);
					nbtList.appendTag(nbtTag);
				}
			}
			nbt.setTag("Inv", nbtList);
		}
	}
}
