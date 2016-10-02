package de.nedelosk.modularmachines.api.modules.containers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;

public class ModuleItemProvider implements IModuleItemProvider{

	protected NBTTagCompound stateTag;

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		if(stateTag != null){
			tag.setTag("State", stateTag.copy());
		}
		return tag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		if(nbt.hasKey("State", 10)){
			stateTag = nbt.getCompoundTag("State");
		}else{
			stateTag = null;
		}
	}

	@Override
	public List<IModuleState> createStates(IModuleProvider provider) {
		if(stateTag == null || stateTag.hasNoTags() || !stateTag.hasKey("Container")){
			return Collections.emptyList();
		}
		List<IModuleState> moduleStates = new ArrayList<>();
		IModuleItemContainer container = ModuleManager.MODULE_CONTAINERS.getValue(new ResourceLocation(stateTag.getString("Container")));
		NBTTagList moduleList = stateTag.getTagList("Modules", 10);
		for(int i = 0; i < moduleList.tagCount(); i++) {
			NBTTagCompound compoundTag = moduleList.getCompoundTagAt(i);
			if(compoundTag != null){
				IModuleState moduleState = ModuleManager.loadStateFromNBT(provider, container, compoundTag);
				if(moduleState != null){
					moduleStates.add(moduleState);
				}
			}
		}
		return moduleStates;
	}

	@Override
	public void setStates(List<IModuleState> states) {
		if(states == null || states.isEmpty()){
			stateTag = null;
		}else{
			stateTag = new NBTTagCompound();
			NBTTagList moduleList = new NBTTagList();
			for(IModuleState moduleState : states){
				NBTTagCompound compoundTag = ModuleManager.writeStateToNBT(moduleState);
				if(compoundTag != null){
					moduleList.appendTag(compoundTag);
				}
			}
			stateTag.setString("Container", states.get(0).getProvider().getContainer().getRegistryName().toString());
			stateTag.setTag("Modules", moduleList);
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
	public boolean hasStates() {
		return stateTag != null;
	}

	@Override
	public IModuleItemContainer getContainer() {
		if(stateTag == null || stateTag.hasNoTags() || !stateTag.hasKey("Container")){
			return null;
		}
		return ModuleManager.MODULE_CONTAINERS.getValue(new ResourceLocation(stateTag.getString("Container")));
	}
}
