package modularmachines.common.modules;

import modularmachines.api.modules.IModuleStorage;
import modularmachines.api.modules.Module;
import net.minecraft.nbt.NBTTagCompound;

public interface IModuleBurning  {
	
	int getFuel();
	
	int getFuelTotal();

}
