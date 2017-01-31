package modularmachines.common.modules.assembler;

import net.minecraft.nbt.NBTTagCompound;

import modularmachines.api.modules.IModuleStorage;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.storages.IStorage;
import modularmachines.api.modules.storages.IStoragePosition;

public class Storage implements IStorage {

	protected final IStoragePosition position;
	protected final ModuleStorage storage;

	public Storage(IStoragePosition position, ModuleStorage storage) {
		this.position = position;
		this.storage = storage;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		storage.writeToNBT(compound);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		storage.readFromNBT(compound);
	}
	
	@Override
	public IStoragePosition getPosition() {
		return position;
	}
	
	@Override
	public Module getModule() {
		return storage.getModuleAtPosition(0);
	}

	@Override
	public IModuleStorage getStorage() {
		return storage;
	}
}
