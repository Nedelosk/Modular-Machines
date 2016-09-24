package de.nedelosk.modularmachines.api.modules.storage;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class Storage implements IStorage{

	protected final IModular modular;
	protected final IStoragePosition position;
	protected final IModuleState storageModule;

	public Storage(IModular modular, IStoragePosition position, IModuleState storageModule) {
		this.modular = modular;
		this.position = position;
		this.storageModule = storageModule;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		return new NBTTagCompound();
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
	}

	@Override
	public IModuleState<IStorageModule> getModule() {
		return storageModule;
	}

	@Override
	public IModular getModular() {
		return modular;
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
