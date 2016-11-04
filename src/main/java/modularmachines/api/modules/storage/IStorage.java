package modularmachines.api.modules.storage;

import javax.annotation.Nonnull;

import modularmachines.api.modular.IModular;
import modularmachines.api.modules.containers.IModuleProvider;
import modularmachines.api.modules.position.IStoragePosition;
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
