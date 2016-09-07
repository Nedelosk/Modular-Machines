package de.nedelosk.modularmachines.api.modules.handlers;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;

public interface IModuleContentProvider {

	List<IModuleContentHandler> getContentHandlers();

	<H> H getContentHandler(Class<? extends H> contentClass);

	NBTTagCompound serializeNBT();

	void deserializeNBT(NBTTagCompound nbt);
}
