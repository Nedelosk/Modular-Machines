package de.nedelosk.modularmachines.api.modules.storage;

import javax.annotation.Nonnull;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.containers.IModuleProvider;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
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
	IModuleProvider getProvider();

	NBTTagCompound serializeNBT();

	void deserializeNBT(NBTTagCompound nbt);

	ItemStack[] toPageStacks();
}
