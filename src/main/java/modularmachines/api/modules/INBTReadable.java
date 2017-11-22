package modularmachines.api.modules;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTReadable {
	void readFromNBT(NBTTagCompound compound);
}