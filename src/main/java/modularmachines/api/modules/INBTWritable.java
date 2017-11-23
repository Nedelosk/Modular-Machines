package modularmachines.api.modules;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Can be used to write the data of a {@link modularmachines.api.modules.components.IModuleComponent} to the NBT-Data
 * of the module.
 */
public interface INBTWritable {
	NBTTagCompound writeToNBT(NBTTagCompound compound);
}