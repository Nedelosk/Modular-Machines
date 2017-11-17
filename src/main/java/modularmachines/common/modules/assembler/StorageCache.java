package modularmachines.common.modules.assembler;

import net.minecraft.nbt.NBTTagCompound;

import modularmachines.api.modules.IModuleStorage;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.storages.IStorage;
import modularmachines.api.modules.storages.IStoragePosition;

public class StorageCache implements IStorage {
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		return null;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
	
	}
	
	@Override
	public IStoragePosition getPosition() {
		return null;
	}
	
	@Override
	public Module getModule() {
		return null;
	}
	
	@Override
	public IModuleStorage getModules() {
		return null;
	}
}
