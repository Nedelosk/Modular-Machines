package de.nedelosk.modularmachines.api.modules.containers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class ModuleItemProvider implements IModuleItemProvider {

	protected final List<IModuleState> moduleStates;
	protected ItemStack itemStack;
	protected IModuleItemContainer container;

	public ModuleItemProvider() {
		this.moduleStates = new ArrayList<>();
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbtCompound = new NBTTagCompound();
		if(itemStack != null){
			nbtCompound.setTag("ItemStack", itemStack.serializeNBT());
		}
		NBTTagList moduleList = new NBTTagList();
		for(IModuleState moduleState : moduleStates){
			NBTTagCompound compoundTag = ModuleManager.writeStateToNBT(moduleState);
			if(compoundTag != null){
				moduleList.appendTag(compoundTag);
			}
		}
		nbtCompound.setTag("Modules", moduleList);
		return nbtCompound;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbtCompound) {
		if(nbtCompound.hasKey("ItemStack")){
			setItemStack(ItemStack.loadItemStackFromNBT(nbtCompound.getCompoundTag("ItemStack")));
		}
		NBTTagList moduleList = nbtCompound.getTagList("Modules", 10);
		for(int i = 0; i < moduleList.tagCount(); i++) {
			NBTTagCompound compoundTag = moduleList.getCompoundTagAt(i);
			if(compoundTag != null){
				IModuleState moduleState = ModuleManager.loadStateFromNBT(null, container, compoundTag);
				if(moduleState != null){
					moduleStates.add(moduleState);
				}
			}
		}
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == ModuleManager.MODULE_PROVIDER_CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == ModuleManager.MODULE_PROVIDER_CAPABILITY){
			return ModuleManager.MODULE_PROVIDER_CAPABILITY.cast(this);
		}
		return null;
	}

	@Override
	public Iterator<IModuleState> iterator() {
		return moduleStates.iterator();
	}

	@Override
	public boolean isEmpty() {
		return moduleStates.isEmpty();
	}

	@Override
	public IModuleItemContainer getContainer() {
		return container;
	}

	@Override
	public ItemStack getItemStack() {
		return itemStack;
	}

	@Override
	public void setItemStack(ItemStack itemStack) {
		this.itemStack = itemStack;
		if(itemStack != null){
			container = ModuleManager.getContainerFromItem(itemStack);
		}
	}

	@Override
	public List<IModuleState> getModuleStates() {
		return Collections.unmodifiableList(moduleStates);
	}

	@Override
	public boolean addModuleState(IModuleState moduleState) {
		return moduleStates.add(moduleState);
	}

	@Override
	public boolean removeModuleState(IModuleState moduleState) {
		return moduleStates.remove(moduleState);
	}
}
