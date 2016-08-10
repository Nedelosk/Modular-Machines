package de.nedelosk.modularmachines.common.modular.positioned;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.api.modular.IPositionedModular;
import de.nedelosk.modularmachines.api.modular.IPositionedModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IPositionedModuleStorage;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import de.nedelosk.modularmachines.common.modular.Modular;
import de.nedelosk.modularmachines.common.modules.storage.PositionedModuleStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class PositionedModular extends Modular implements IPositionedModular {

	protected EnumMap<EnumPosition, IPositionedModuleStorage> storages;

	public PositionedModular() {
		super();
		if(storages == null){
			storages = new EnumMap(EnumPosition.class);
		}
	}

	public PositionedModular(NBTTagCompound nbt, IModularHandler handler) {
		super(nbt, handler);
		if(storages == null){
			storages = new EnumMap(EnumPosition.class);
		}
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		if(storages == null){
			storages = new EnumMap(EnumPosition.class);
		}
		NBTTagList nbtList = nbt.getTagList("Storages", 10);
		for(int i = 0; i < nbtList.tagCount(); i++) {
			NBTTagCompound moduleTag = nbtList.getCompoundTagAt(i);
			EnumPosition position = EnumPosition.values()[moduleTag.getShort("Position")];
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
		ItemStack[] moduleStacks = new ItemStack[26];
		for(IPositionedModuleStorage moduleStorage : storages.values()){
			if(moduleStorage != null){
				EnumPosition position = moduleStorage.getPosition();
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
	public IPositionedModuleStorage getModuleStorage(EnumPosition position) {
		return storages.get(position);
	}

	@Override
	public void setModuleStorage(EnumPosition position, IPositionedModuleStorage storage) {
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
	public <M extends IModule> IModuleState<M> getModule(int index) {
		for(IPositionedModuleStorage moduleStorage : storages.values()){
			if(moduleStorage != null){
				IModuleState state = moduleStorage.getModule(index);
				if(state != null){
					return state;
				}
			}
		}
		return null;
	}

	@Override
	public Collection<IPositionedModuleStorage> getModuleStorages() {
		return storages.values();
	}

	@Override
	public <M extends IModule> List<IModuleState<M>> getModules(Class<? extends M> moduleClass) {
		if (moduleClass == null) {
			return null;
		}
		List<IModuleState<M>> modules = Lists.newArrayList();
		for(IPositionedModuleStorage moduleStorage : storages.values()){
			if(moduleStorage != null){
				modules.addAll(moduleStorage.getModules(moduleClass));
			}
		}
		return modules;
	}

	@Override
	public <M extends IModule> IModuleState<M> getModule(Class<? extends M> moduleClass) {
		if (moduleClass == null) {
			return null;
		}
		List modules = getModules(moduleClass);
		if(modules.isEmpty()){
			return null;
		}
		return (IModuleState<M>) modules.get(0);
	}

	@Override
	public IPositionedModular copy(IModularHandler handler) {
		return new PositionedModular(serializeNBT(), handler);
	}
}