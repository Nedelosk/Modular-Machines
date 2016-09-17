package de.nedelosk.modularmachines.api.modules.storage.module;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.EnumModuleSizes;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.Storage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ModuleStorage extends Storage implements IBasicModuleStorage, IDefaultModuleStorage, IAddableModuleStorage{

	protected final List<IModuleState> moduleStates;
	protected final EnumModuleSizes size;
	protected final boolean isAddable;

	public ModuleStorage(IModular modular, IStoragePosition position, IModuleState storageModule, EnumModuleSizes size) {
		this(modular, position, storageModule, size, false);
	}

	public ModuleStorage(IModular modular, IStoragePosition position, IModuleState storageModule, EnumModuleSizes size, boolean isAddable) {
		super(modular, position, storageModule);
		this.moduleStates = new ArrayList<>();
		this.size = size;
		this.isAddable = isAddable;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		NBTTagList nbtList = new NBTTagList();
		for(IModuleState moduleState : moduleStates) {
			NBTTagCompound compoundTag = ModularMachinesApi.writeStateToNBT(modular, moduleState);
			if(compoundTag != null){
				nbtList.appendTag(compoundTag);
			}
		}
		nbt.setTag("Modules", nbtList);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		NBTTagList nbtList = nbt.getTagList("Modules", 10);
		for(int i = 0; i < nbtList.tagCount(); i++) {
			IModuleState moduleState = ModularMachinesApi.loadStateFromNBT(modular, nbtList.getCompoundTagAt(i));
			if(moduleState != null){
				moduleStates.add(moduleState);
			}
		}
	}

	@Override
	public List<IModuleState> getModules() {
		return moduleStates;
	}

	@Override
	public <M extends IModule> IModuleState<M> getModule(int index) {
		for(IModuleState module : moduleStates) {
			if (module.getIndex() == index) {
				return module;
			}
		}
		return null;
	}

	@Override
	public <M extends IModule> List<IModuleState<M>> getModules(Class<? extends M> moduleClass) {
		if (moduleClass == null) {
			return null;
		}
		List<IModuleState<M>> modules = Lists.newArrayList();
		for(IModuleState module : moduleStates) {
			if (moduleClass.isAssignableFrom(module.getModule().getClass())) {
				modules.add(module);
			}
		}
		return modules;
	}

	@Override
	public <M extends IModule> IModuleState<M> getModule(Class<? extends M> moduleClass) {
		List<IModuleState<M>> modules = getModules(moduleClass);
		if(modules.isEmpty()){
			return null;
		}
		return modules.get(0);
	}

	@Override
	public int getComplexity(boolean withStorage) {
		int complexity = 0;
		for(IModuleState state : getModules()){
			if(state != null){	
				if(state.getModule() instanceof IModuleModuleStorage && !withStorage){
					continue;
				}
				complexity +=state.getModule().getComplexity(state.getContainer());
			}
		}
		return complexity;
	}


	@Override
	public boolean addModule(ItemStack itemStack, IModuleState state) {
		if (!isAddable || state == null) {
			return false;
		}

		if (moduleStates.add(state)) {
			state.setIndex(modular.getNextIndex());
		}
		return true;
	}

	@Override
	public EnumModuleSizes getSize() {
		return size;
	}

	@Override
	public ItemStack[] toPageStacks() {
		ItemStack[] stacks = new ItemStack[size.slots];
		int index = 0;
		for(IModuleState state : moduleStates){
			if(state != null){
				stacks[index] = ModularMachinesApi.saveModuleState(state);
				index+= state.getModule().getSize(state.getContainer()).slots;
			}
		}
		return stacks;
	}
}
