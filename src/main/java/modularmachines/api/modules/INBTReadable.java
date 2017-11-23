package modularmachines.api.modules;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Can be used to read the data of a {@link modularmachines.api.modules.components.IModuleComponent} from the NBT-Data
 * of the module.
 */
public interface INBTReadable {
	void readFromNBT(NBTTagCompound compound);
}