package de.nedelosk.modularmachines.api.modules.storage;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleModuleStorage;
import de.nedelosk.modularmachines.api.modules.ModuleEvents;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

public class AdvancedModuleStorage implements IAdvancedModuleStorage{

	protected final List<IModuleState> moduleStates = new ArrayList<>();
	protected final IModular modular;

	public AdvancedModuleStorage(IModular modular) {
		this.modular = modular;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		NBTTagList nbtList = new NBTTagList();
		for(IModuleState module : moduleStates) {
			NBTTagCompound nbtTag = module.serializeNBT();
			nbtTag.setString("Container", module.getContainer().getRegistryName().toString());
			MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleStateSaveEvent(module, nbtTag));
			nbtList.appendTag(nbtTag);
		}
		nbt.setTag("Modules", nbtList);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		NBTTagList nbtList = nbt.getTagList("Modules", 10);
		for(int i = 0; i < nbtList.tagCount(); i++) {
			NBTTagCompound moduleTag = nbtList.getCompoundTagAt(i);
			ResourceLocation loc = new ResourceLocation(moduleTag.getString("Container"));
			IModuleContainer container = ModularMachinesApi.MODULE_CONTAINERS.getValue(loc);
			if(container != null){
				IModuleState state = ModularMachinesApi.createModuleState(modular, container);
				state.deserializeNBT(moduleTag);
				MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleStateLoadEvent(state, moduleTag));
				moduleStates.add(state);
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
		return (IModuleState<M>) getModules(moduleClass).get(0);
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
}
