package de.nedelosk.modularmachines.api.modules.items;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.ModuleEvents;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;

public class ModuleProvider implements IModuleProvider{

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
	public IModuleState createState(IModular modular) {
		if(stateTag == null || stateTag.hasNoTags()){
			return null;
		}
		ResourceLocation loc = new ResourceLocation(stateTag.getString("Container"));
		IModuleContainer container = ModularMachinesApi.MODULE_CONTAINERS.getValue(loc);
		if(container != null){
			IModuleState state = ModularMachinesApi.createModuleState(modular, container);
			state.deserializeNBT(stateTag);
			MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleStateLoadEvent(state, stateTag));
			return state;
		}
		return null;
	}

	@Override
	public void setState(IModuleState state) {
		if(state == null){
			stateTag = null;
		}else{
			stateTag = state.serializeNBT();
			stateTag.setString("Container", state.getContainer().getRegistryName().toString());
			MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleStateSaveEvent(state, stateTag));
		}
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == ModularMachinesApi.MODULE_PROVIDER_CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == ModularMachinesApi.MODULE_PROVIDER_CAPABILITY){
			return ModularMachinesApi.MODULE_PROVIDER_CAPABILITY.cast(this);
		}
		return null;
	}

	@Override
	public boolean hasState() {
		return stateTag != null;
	}
}
