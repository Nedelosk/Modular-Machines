package de.nedelosk.modularmachines.api.modules.items;

import javax.annotation.Nullable;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

/**
 * This is used to save a module state.
 */
public interface IModuleProvider extends ICapabilitySerializable<NBTTagCompound> {

	@Nullable
	IModuleState getState();

	void setState(IModuleState state);

}
