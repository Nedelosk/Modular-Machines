package modularmachines.api.modules.storages;

import modularmachines.api.modules.IModuleStorage;
import modularmachines.api.modules.Module;
import net.minecraft.nbt.NBTTagCompound;

public interface IStorage {
	
    NBTTagCompound writeToNBT(NBTTagCompound compound);
    
    void readFromNBT(NBTTagCompound compound);
	
	IStoragePosition getPosition();

	Module getModule();
	
	IModuleStorage getStorage();
	
}
