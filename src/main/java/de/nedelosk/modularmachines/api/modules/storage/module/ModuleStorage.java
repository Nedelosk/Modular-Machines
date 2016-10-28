package de.nedelosk.modularmachines.api.modules.storage.module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.api.ItemUtil;
import de.nedelosk.modularmachines.api.modules.EnumModuleSizes;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.api.modules.containers.IModuleItemContainer;
import de.nedelosk.modularmachines.api.modules.containers.IModuleProvider;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.Storage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;

public class ModuleStorage extends Storage implements IBasicModuleStorage, IDefaultModuleStorage, IAddableModuleStorage {

	protected List<IModuleProvider> providers;
	protected final EnumModuleSizes size;
	protected final boolean isAddable;

	public ModuleStorage(IStoragePosition position, IModuleProvider storageProvider, EnumModuleSizes size) {
		this(position, storageProvider, size, false);
	}

	public ModuleStorage(IStoragePosition position, IModuleProvider storageProvider, EnumModuleSizes size, boolean isAddable) {
		super(position, storageProvider);
		this.providers = new ArrayList<>();
		this.size = size;
		this.isAddable = isAddable;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		NBTTagList containerList = new NBTTagList();
		for(IModuleProvider provider : providers) {
			NBTTagCompound nbtCompound = provider.serializeNBT();
			if (!ItemUtil.isIdenticalItem(provider.getItemStack(), provider.getContainer().getItemStack())) {
				nbtCompound.setTag("Item", provider.getItemStack().serializeNBT());
			}
			nbtCompound.setString("Container", provider.getContainer().getRegistryName().toString());
			containerList.appendTag(nbtCompound);
		}
		nbtTag.setTag("Providers", containerList);
		return nbtTag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		NBTTagList containerList = nbt.getTagList("Providers", 10);
		for(int i = 0; i < containerList.tagCount(); i++) {
			NBTTagCompound nbtCompound = containerList.getCompoundTagAt(i);
			IModuleItemContainer itemContainer = ModuleManager.MODULE_CONTAINERS.getValue(new ResourceLocation(nbtCompound.getString("Container")));
			ItemStack itemStack = null;
			if (nbtCompound.hasKey("Item")) {
				itemStack = ItemStack.loadItemStackFromNBT(nbtCompound.getCompoundTag("Item"));
			}
			IModuleProvider provider = itemContainer.createModuleProvider(itemContainer, getModular(), itemStack);
			provider.deserializeNBT(nbtCompound);
			providers.add(provider);
		}
	}

	@Override
	public List<IModuleState> getModules() {
		List<IModuleState> moduleStates = new ArrayList<>();
		for(IModuleProvider states : providers) {
			moduleStates.addAll(states.getModuleStates());
		}
		return Collections.unmodifiableList(moduleStates);
	}

	@Override
	public <M extends IModule> IModuleState<M> getModule(int index) {
		for(IModuleState module : getModules()) {
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
		for(IModuleState module : getModules()) {
			if (moduleClass.isAssignableFrom(module.getModule().getClass())) {
				modules.add(module);
			}
		}
		return Collections.unmodifiableList(modules);
	}

	@Override
	public <M extends IModule> IModuleState<M> getModule(Class<? extends M> moduleClass) {
		List<IModuleState<M>> modules = getModules(moduleClass);
		if (modules.isEmpty()) {
			return null;
		}
		return modules.get(0);
	}

	@Override
	public int getComplexity(boolean withStorage) {
		int complexity = 0;
		for(IModuleState state : getModules()) {
			if (state != null) {
				if (state.getModule() instanceof IModuleModuleStorage && !withStorage) {
					continue;
				}
				complexity += state.getModule().getComplexity(state.getContainer());
			}
		}
		return complexity;
	}

	@Override
	public boolean addModule(IModuleProvider provider) {
		if (!isAddable || provider == null) {
			return false;
		}
		if (providers.add(provider)) {
			for(IModuleState state : provider.getModuleStates()) {
				state.setIndex(getModular().getNextIndex());
			}
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
		for(IModuleProvider provider : providers) {
			if (provider != null) {
				stacks[index] = ModuleManager.saveModuleStateToItem(provider);
				index += provider.getContainer().getSize().slots;
			}
		}
		return stacks;
	}

	@Override
	public List<IModuleProvider> getProviders() {
		return providers;
	}
}
