package nedelosk.modularmachines.api.modules.container.inventory;

import java.util.ArrayList;
import java.util.List;

import nedelosk.modularmachines.api.modular.basic.IModularInventory;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleDefault;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.modules.container.module.IMultiModuleContainer;
import nedelosk.modularmachines.api.modules.inventory.IModuleInventory;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class MultiInventoryContainer<M extends IModule, S extends IModuleSaver> implements IMultiInventoryContainer<M, S, List<IModuleInventory<M, S>>> {

	private List<IModuleInventory<M, S>> inventorys = new ArrayList();
	private String categoryUID;

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
	public void readFromNBT(NBTTagCompound nbt, IModularInventory modular) {
		categoryUID = nbt.getString("CategoryUID");
		if (nbt.hasKey("Inv")) {
			NBTTagList nbtList = nbt.getTagList("Inv", 10);
			inventorys = new ArrayList<IModuleInventory<M, S>>();
			IMultiModuleContainer container = modular.getModuleManager().getMultiModule(categoryUID);
			for ( int i = 0; i < nbtList.tagCount(); i++ ) {
				NBTTagCompound nbtTag = nbtList.getCompoundTagAt(i);
				ModuleStack stackModule = container.getStack(nbtTag.getString("ModuleUID"));
				IModuleInventory inv = ((IModuleDefault) stackModule.getModule()).createInventory(stackModule);
				inv.readFromNBT(nbtTag, modular, stackModule);
				inventorys.add(inv);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModularInventory modular) {
		nbt.setString("CategoryUID", categoryUID);
		if (inventorys != null && inventorys.isEmpty()) {
			IMultiModuleContainer container = modular.getModuleManager().getMultiModule(categoryUID);
			NBTTagList nbtList = new NBTTagList();
			for ( IModuleInventory<M, S> inv : inventorys ) {
				if (inv != null) {
					NBTTagCompound nbtTag = new NBTTagCompound();
					nbtTag.setString("ModuleUID", inv.getModuleUID());
					inv.writeToNBT(nbtTag, modular, container.getStack(inv.getModuleUID()));
					nbtList.appendTag(nbtTag);
				}
			}
			nbt.setTag("Inv", nbtList);
		}
	}
	
	public String getCategoryUID() {
		return categoryUID;
	}
	
	@Override
	public void setCategoryUID(String categoryUID) {
		this.categoryUID = categoryUID;
	}
}
