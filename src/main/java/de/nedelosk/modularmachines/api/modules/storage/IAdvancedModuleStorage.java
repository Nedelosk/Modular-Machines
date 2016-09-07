package de.nedelosk.modularmachines.api.modules.storage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface IAdvancedModuleStorage extends IModuleStorage, INBTSerializable<NBTTagCompound> {

	/**
	 * @return The complexity of all module in this storage.
	 */
	int getComplexity(boolean withStorage);
}
