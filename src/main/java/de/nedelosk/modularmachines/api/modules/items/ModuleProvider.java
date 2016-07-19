package de.nedelosk.modularmachines.api.modules.items;

import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class ModuleProvider implements IModuleProvider{

	public IModuleState state;

	public ModuleProvider() {
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound tag;
		if(state != null){
			tag = state.serializeNBT();
		}else{
			tag = new NBTTagCompound();
		}
		tag.setBoolean("State", state != null);
		return tag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		if(nbt.getBoolean("State")){
			state.deserializeNBT(nbt);
		}
	}

	@Override
	public IModuleState getState() {
		return state;
	}

	@Override
	public void setState(IModuleState state) {
		this.state = state;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == ModularManager.MODULE_PROVIDER_CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == ModularManager.MODULE_PROVIDER_CAPABILITY){
			return ModularManager.MODULE_PROVIDER_CAPABILITY.cast(this);
		}
		return null;
	}
}
