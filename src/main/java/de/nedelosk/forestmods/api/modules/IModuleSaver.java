package de.nedelosk.forestmods.api.modules;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IModuleSaver {

	void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack);

	void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack);
}
