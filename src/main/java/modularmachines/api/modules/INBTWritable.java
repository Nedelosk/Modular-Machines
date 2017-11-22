package modularmachines.api.modules;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTWritable {
	NBTTagCompound writeToNBT(NBTTagCompound compound);
}