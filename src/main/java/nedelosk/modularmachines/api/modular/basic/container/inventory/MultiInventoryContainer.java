package nedelosk.modularmachines.api.modular.basic.container.inventory;

import java.util.ArrayList;
import java.util.List;

import nedelosk.modularmachines.api.modular.basic.IModularInventory;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.inventory.IModuleInventory;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class MultiInventoryContainer<M extends IModule> implements IMultiInventoryContainer<M, List<IModuleInventory<M>>> {

	private List<IModuleInventory<M>> inventorys = new ArrayList();

	public MultiInventoryContainer(IModuleInventory<M> inventory) {
		inventorys.add(inventory);
	}

	public MultiInventoryContainer() {
	}

	@Override
	public void addInventory(int index, IModuleInventory<M> stack) {
		inventorys.add(index, stack);
	}

	@Override
	public void addInventory(IModuleInventory<M> inv) {
		inventorys.add(inv);
	}

	@Override
	public void setInventorys(List<IModuleInventory<M>> collection) {
		this.inventorys = collection;
	}

	@Override
	public IModuleInventory<M> getInventory(int index) {
		return inventorys.get(index);
	}

	@Override
	public IModuleInventory<M> getInventory(String moduleUID) {
		for ( IModuleInventory<M> inv : inventorys ) {
			if (inv.getModuleUID().equals(moduleUID)) {
				return inv;
			}
		}
		return null;
	}

	@Override
	public int getIndex(IModuleInventory<M> inv) {
		if (inv == null) {
			return -1;
		}
		return inventorys.indexOf(inv);
	}

	@Override
	public List<IModuleInventory<M>> getInventorys() {
		return inventorys;
	}

	/* NBT */
	@Override
	public void readFromNBT(NBTTagCompound nbt, IModularInventory modular, ModuleStack stack) throws Exception {
		if (nbt.hasKey("Inv")) {
			NBTTagList nbtList = nbt.getTagList("Inv", 10);
			inventorys = new ArrayList<IModuleInventory<M>>();
			for ( int i = 0; i < nbtList.tagCount(); i++ ) {
				NBTTagCompound nbtTag = nbtList.getCompoundTagAt(i);
				IModuleInventory inv = stack.getModule().createInventory(stack);
				inv.readFromNBT(nbtTag, modular, stack);
				inventorys.add(inv);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModularInventory modular, ModuleStack stack) throws Exception {
		if (inventorys != null && inventorys.isEmpty()) {
			NBTTagList nbtList = new NBTTagList();
			for ( IModuleInventory<M> inv : inventorys ) {
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
