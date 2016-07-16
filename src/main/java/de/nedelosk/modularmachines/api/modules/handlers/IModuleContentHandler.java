package de.nedelosk.modularmachines.api.modules.handlers;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.nbt.NBTTagCompound;

public interface IModuleContentHandler {

	/**
	 * @return The state of the module of the handler.
	 */
	IModuleState getModuleState();

	String getHandlerUID();

	void readFromNBT(NBTTagCompound nbt);

	NBTTagCompound writeToNBT(NBTTagCompound nbt);
}
