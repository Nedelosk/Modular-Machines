package de.nedelosk.modularmachines.api.modules.storage;

import java.util.List;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

/**
 * A storage to storage module states. It is used in a modular and other thinks that have module states, to storage these.
 */
public interface IModuleStorage extends ICapabilitySerializable<NBTTagCompound>{

	/**
	 * Add a module state to the storage and set the index of the module state.
	 */
	IModuleState addModule(ItemStack itemStack, IModuleState state);

	/**
	 * @return All module states that are have a module that class is a instance of of the moduleClass.
	 */
	<M extends IModule> List<IModuleState<M>> getModules(Class<? extends M> moduleClass);

	/**
	 * 
	 * @return A module state that have the index.
	 */
	<M extends IModule> IModuleState<M> getModule(int index);

}
