package de.nedelosk.forestmods.library.recipes;

import net.minecraft.nbt.NBTTagCompound;

public interface IRecipeNBTSerializer {

	void serializeNBT(NBTTagCompound nbt, Object[] craftingModifiers);

	Object[] deserializeNBT(NBTTagCompound nbt);
}
