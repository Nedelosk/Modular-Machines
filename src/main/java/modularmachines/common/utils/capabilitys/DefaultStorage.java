package modularmachines.common.utils.capabilitys;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;

import modularmachines.api.modules.container.IModuleContainer;

public class DefaultStorage implements Capability.IStorage<IModuleContainer> {
	@Override
	public NBTBase writeNBT(Capability capability, IModuleContainer instance, EnumFacing side) {
		return instance.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void readNBT(Capability capability, IModuleContainer instance, EnumFacing side, NBTBase nbt) {
		if (!(nbt instanceof NBTTagCompound)) {
			return;
		}
		instance.readFromNBT((NBTTagCompound) nbt);
	}
}
