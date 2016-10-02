package de.nedelosk.modularmachines.api.modules.storage;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.containers.IModuleProvider;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class Storage implements IStorage{

	protected final IStoragePosition position;
	protected final IModuleProvider storageProvider;

	public Storage(IStoragePosition position, IModuleProvider storageProvider) {
		this.position = position;
		this.storageProvider = storageProvider;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		return new NBTTagCompound();
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
	}

	@Override
	public IModuleProvider getProvider() {
		return storageProvider;
	}

	@Override
	public IModular getModular() {
		return storageProvider.getModular();
	}

	@Override
	public IStoragePosition getPosition() {
		return position;
	}

	@Override
	public ItemStack[] toPageStacks() {
		return new ItemStack[0];
	}
}
