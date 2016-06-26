package de.nedelosk.modularmachines.common.modular;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ModularProvider implements ICapabilityProvider {

	public IModular modular;

	public ModularProvider(IModular modular) {
		this.modular = modular;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == ModuleManager.MODULAR_CAPABILITY){
			return true;
		}
		return false;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == ModuleManager.MODULAR_CAPABILITY){
			return (T) modular;
		}
		return null;
	}

}
