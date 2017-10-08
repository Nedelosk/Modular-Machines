package modularmachines.api.modules.storages;

import net.minecraft.nbt.NBTTagCompound;

import modularmachines.api.modules.IModuleStorage;
import modularmachines.api.modules.Module;

public interface IStorage {
	
    NBTTagCompound writeToNBT(NBTTagCompound compound);
    
    void readFromNBT(NBTTagCompound compound);
	
	IStoragePosition getPosition();

	Module getModule();
	
	IModuleStorage getModules();
	
}
