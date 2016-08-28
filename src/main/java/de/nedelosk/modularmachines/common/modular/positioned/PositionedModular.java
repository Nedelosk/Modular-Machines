package de.nedelosk.modularmachines.common.modular.positioned;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.api.modular.IPositionedModular;
import de.nedelosk.modularmachines.api.modular.IPositionedModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.EnumStoragePosition;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IPositionedModuleStorage;
import de.nedelosk.modularmachines.common.modular.Modular;
import de.nedelosk.modularmachines.common.modules.storage.PositionedModuleStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class PositionedModular extends Modular implements IPositionedModular {

	protected EnumMap<EnumStoragePosition, IPositionedModuleStorage> storages;

	public PositionedModular() {
		super();
		if(storages == null){
			storages = new EnumMap(EnumStoragePosition.class);
		}
	}

	public PositionedModular(NBTTagCompound nbt, IModularHandler handler) {
		super(nbt, handler);
		if(storages == null){
			storages = new EnumMap(EnumStoragePosition.class);
		}
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		if(storages == null){
			storages = new EnumMap(EnumStoragePosition.class);
		}
		NBTTagList nbtList = nbt.getTagList("Storages", 10);
		for(int i = 0; i < nbtList.tagCount(); i++) {
			NBTTagCompound moduleTag = nbtList.getCompoundTagAt(i);
			EnumStoragePosition position = EnumStoragePosition.values()[moduleTag.getShort("Position")];
			IPositionedModuleStorage storage = new PositionedModuleStorage(this, position);
			storage.deserializeNBT(moduleTag);
			storages.put(position, storage);
		}
		super.deserializeNBT(nbt);
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = super.serializeNBT();
		NBTTagList nbtList = new NBTTagList();
		for(IPositionedModuleStorage storage : storages.values()) {
			if(storage != null){
				NBTTagCompound nbtTag = storage.serializeNBT();
				nbtTag.setShort("Position", (short) storage.getPosition().ordinal());
				nbtList.appendTag(nbtTag);
			}
		}
		nbt.setTag("Storages", nbtList);
		return nbt;
	}

	@Override
	public IPositionedModularAssembler disassemble() {
		if(modularHandler instanceof IModularHandlerTileEntity){
			((IModularHandlerTileEntity)modularHandler).invalidate();
		}
		ItemStack[] moduleStacks = new ItemStack[26];
		for(IPositionedModuleStorage moduleStorage : storages.values()){
			if(moduleStorage != null){
				EnumStoragePosition position = moduleStorage.getPosition();
				int index = 0;
				for(IModuleState state : moduleStorage.getModules()) {
					moduleStacks[position.startSlotIndex + index] = ModularMachinesApi.saveModuleState(state);
					index++;
				}
			}
		}
		return new PositionedModularAssembler(modularHandler, moduleStacks);
	}

	@Override
	public IPositionedModuleStorage getModuleStorage(EnumStoragePosition position) {
		return storages.get(position);
	}

	@Override
	public void setModuleStorage(EnumStoragePosition position, IPositionedModuleStorage storage) {
		storages.put(position, storage);
	}

	@Override
	public List<IModuleState> getModules() {
		List<IModuleState> modules = new ArrayList<>();
		for(IPositionedModuleStorage moduleStorage : storages.values()){
			if(moduleStorage != null){
				modules.addAll(moduleStorage.getModules());
			}
		}
		return modules;
	}

	@Override
	public Collection<IPositionedModuleStorage> getModuleStorages() {
		return storages.values();
	}

	@Override
	public IPositionedModular copy(IModularHandler handler) {
		return new PositionedModular(serializeNBT(), handler);
	}
}