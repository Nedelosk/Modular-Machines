package modularmachines.api.modules.containers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import modularmachines.api.modular.IModular;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.state.IModuleState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ModuleProvider implements IModuleProvider {

	private final IModuleItemContainer itemContainer;
	private final IModular modular;
	private final ItemStack itemStack;
	private final List<IModuleState> moduleStates;

	public ModuleProvider(IModuleItemContainer itemContainer, IModular modular, ItemStack itemStack) {
		this.itemContainer = itemContainer;
		this.modular = modular;
		this.itemStack = itemStack;
		this.moduleStates = new ArrayList<>();
	}

	@Override
	public IModuleItemContainer getContainer() {
		return itemContainer;
	}

	@Override
	public void addModuleState(IModuleState moduleState) {
		if (moduleState.getProvider() != this) {
			moduleState.setProvider(this);
		}
		moduleStates.add(moduleState);
	}

	@Override
	public List<IModuleState> getModuleStates() {
		return moduleStates;
	}

	@Override
	public Iterator<IModuleState> iterator() {
		return moduleStates.iterator();
	}

	@Override
	public ItemStack getItemStack() {
		if (itemStack == null) {
			return itemContainer.getItemStack();
		}
		return itemStack;
	}

	@Override
	public IModular getModular() {
		return modular;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbtCompound = new NBTTagCompound();
		NBTTagList moduleList = new NBTTagList();
		for(IModuleState moduleState : moduleStates) {
			NBTTagCompound compoundTag = ModuleManager.writeStateToNBT(moduleState);
			if (compoundTag != null) {
				moduleList.appendTag(compoundTag);
			}
		}
		nbtCompound.setTag("Modules", moduleList);
		return nbtCompound;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbtCompound) {
		NBTTagList moduleList = nbtCompound.getTagList("Modules", 10);
		for(int i = 0; i < moduleList.tagCount(); i++) {
			NBTTagCompound compoundTag = moduleList.getCompoundTagAt(i);
			if (compoundTag != null) {
				IModuleState moduleState = ModuleManager.loadStateFromNBT(this, itemContainer, compoundTag);
				if (moduleState != null) {
					moduleStates.add(moduleState);
				}
			}
		}
	}
}
