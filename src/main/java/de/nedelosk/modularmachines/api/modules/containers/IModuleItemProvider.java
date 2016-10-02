package de.nedelosk.modularmachines.api.modules.containers;

import java.util.List;

import javax.annotation.Nonnull;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

/**
 * This is used to save a module state.
 */
public interface IModuleItemProvider extends ICapabilitySerializable<NBTTagCompound> {

	@Nonnull
	List<IModuleState> createStates(IModuleProvider provider);

	void setStates(List<IModuleState> state);

	IModuleItemContainer getContainer();

	boolean hasStates();

}
