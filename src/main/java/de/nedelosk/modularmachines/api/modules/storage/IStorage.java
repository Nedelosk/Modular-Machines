package de.nedelosk.modularmachines.api.modules.storage;

import javax.annotation.Nonnull;

import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.module.IAddableModuleStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * It stores his own parent and other thinks.
 */
public interface IStorage {

	IStoragePosition getPosition();

	@Nonnull
	IModular getModular();

	@Nonnull
	IModuleState<IStorageModule> getModule();

	NBTTagCompound serializeNBT();

	void deserializeNBT(NBTTagCompound nbt);
	
	ItemStack[] toPageStacks();

}
