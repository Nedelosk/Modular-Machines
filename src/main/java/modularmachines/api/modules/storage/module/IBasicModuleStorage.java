package modularmachines.api.modules.storage.module;

import net.minecraft.nbt.NBTTagCompound;

public interface IBasicModuleStorage extends IModuleStorage {

	/**
	 * @return The complexity of all module in this storage.
	 */
	int getComplexity(boolean withStorage);

	NBTTagCompound serializeNBT();

	void deserializeNBT(NBTTagCompound nbt);
}
