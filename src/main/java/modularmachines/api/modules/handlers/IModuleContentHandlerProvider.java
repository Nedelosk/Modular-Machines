package modularmachines.api.modules.handlers;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;

public interface IModuleContentHandlerProvider {

	List<IModuleContentHandler> getContentHandlers();

	<H> H getContentHandler(Class<? extends H> handlerClass);

	NBTTagCompound serializeNBT();

	void deserializeNBT(NBTTagCompound nbt);
}
