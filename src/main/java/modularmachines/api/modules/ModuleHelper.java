package modularmachines.api.modules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;

import modularmachines.api.modular.IModular;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.containers.IModuleItemContainer;
import modularmachines.api.modules.containers.IModuleItemProvider;
import modularmachines.api.modules.containers.IModuleProvider;
import modularmachines.api.modules.containers.ModuleProvider;
import modularmachines.api.modules.position.IStoragePosition;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.modules.storage.IStorageModule;

public class ModuleHelper {

	/**
	 * @return All modules of an IModular that have a page.
	 */
	public static List<IModuleState> getPageModules(@Nullable IModular modular) {
		if (modular == null) {
			return Collections.emptyList();
		}
		List<IModuleState> validModules = Lists.newArrayList();
		for (IModuleState moduleState : modular.getModules()) {
			if (moduleState != null && moduleState.hasPages()) {
				validModules.add(moduleState);
			}
		}
		return validModules;
	}

	/**
	 * @return The matching module container for the stack.
	 */
	public static IModuleItemContainer getContainerFromItem(ItemStack stack) {
		return getContainerFromItem(stack, null);
	}

	/**
	 * @return The matching module container for the stack.
	 */
	public static IModuleItemContainer getContainerFromItem(ItemStack stack, IModular modular) {
		if (stack == null) {
			return null;
		}
		IModuleItemContainer itemContainer = getContainerFromProvider(modular, stack);
		if (itemContainer != null) {
			return itemContainer;
		}
		for (IModuleItemContainer container : ModuleManager.MODULE_CONTAINERS) {
			if (container.matches(stack)) {
				return container;
			}
		}
		return null;
	}

	public static NBTTagCompound writeStateToNBT(IModuleState moduleState) {
		if (moduleState != null && moduleState.getContainer() != null) {
			NBTTagCompound compoundTag = moduleState.serializeNBT();
			compoundTag.setInteger("Index", moduleState.getContainer().getIndex());
			MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleStateSaveEvent(moduleState, compoundTag));
			return compoundTag;
		}
		return null;
	}

	public static IModuleState loadStateFromNBT(IModuleProvider provider, IModuleItemContainer itemContainer, NBTTagCompound compoundTag) {
		if (itemContainer != null) {
			IModuleState state = createModuleState(provider, itemContainer, compoundTag.getInteger("Index"));
			state.deserializeNBT(compoundTag);
			MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleStateLoadEvent(state, compoundTag));
			return state;
		}
		return null;
	}

	/**
	 * Save a module state to the module provider capability in the item stack.
	 */
	public static ItemStack saveModuleStateToItem(IModuleProvider provider) {
		if (provider != null) {
			ItemStack stack = provider.getItemStack().copy();
			for (IModuleState state : provider.getModuleStates()) {
				if (state != null) {
					state.getModule().saveDataToItem(stack, state);
				}
			}
			ItemStack itemModuleContainer = provider.getContainer().createModuleItemContainer();
			if (itemModuleContainer.hasCapability(ModuleManager.MODULE_PROVIDER_CAPABILITY, null)) {
				IModuleItemProvider itemProvider = itemModuleContainer.getCapability(ModuleManager.MODULE_PROVIDER_CAPABILITY, null);
				itemProvider.setItemStack(stack);
				if (provider != null) {
					List<IModuleState> moduleStates = new ArrayList<>();
					for (IModuleState state : provider.getModuleStates()) {
						if (!state.getModule().isClean(state)) {
							moduleStates.add(state);
						}
					}
					if (!moduleStates.isEmpty()) {
						for (IModuleState moduleState : moduleStates) {
							itemProvider.addModuleState(moduleState);
						}
					}
				}
				if (!itemProvider.isEmpty()) {
					return itemModuleContainer;
				}
				return itemProvider.getItemStack();
			}
		}
		return null;
	}

	public static IModuleItemContainer getContainerFromProvider(IModular modular, ItemStack stack) {
		IModuleItemProvider provider = getProviderFromItem(modular, stack);
		if (provider != null) {
			return provider.getContainer();
		}
		return null;
	}

	public static IModuleItemProvider getProviderFromItem(IModular modular, ItemStack stack) {
		if (stack != null && stack.hasCapability(ModuleManager.MODULE_PROVIDER_CAPABILITY, null)) {
			return stack.getCapability(ModuleManager.MODULE_PROVIDER_CAPABILITY, null);
		}
		return null;
	}

	public static IModuleState createModuleState(IModuleProvider provider, IModuleItemContainer itemContainer, int index) {
		if (itemContainer != null) {
			IModuleContainer container = itemContainer.getContainer(index);
			if (container != null) {
				IModuleState state = container.getModule().createState(provider, container);
				if (state != null) {
					MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleStateCreateEvent(state));
					return state.init();
				}
			}
		}
		return null;
	}

	public static List<IModuleState> createModuleStates(IModuleProvider provider, IModuleItemContainer itemContainer) {
		List<IModuleState> states = new ArrayList<>();
		if (itemContainer != null) {
			for (IModuleContainer container : itemContainer.getContainers()) {
				if (container != null) {
					IModuleState state = container.getModule().createState(provider, container);
					if (state != null) {
						MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleStateCreateEvent(state));
						states.add(state.init());
					}
				}
			}
			return states;
		}
		return states;
	}

	public static IModuleState<IStorageModule> getStorageState(IModuleProvider provider, IStoragePosition position) {
		for (IModuleState moduleState : provider.getModuleStates()) {
			IModule module = moduleState.getModule();
			if (module instanceof IStorageModule) {
				IStorageModule storageModule = (IStorageModule) module;
				if (storageModule.isValidForPosition(position, moduleState.getContainer())) {
					return moduleState;
				}
			}
		}
		return null;
	}

	public static IModuleContainer<IStorageModule, IModuleProperties> getStorageContainer(IModuleItemContainer itemContainer, IStoragePosition position) {
		if (itemContainer == null) {
			return null;
		}
		for (IModuleContainer moduleContainer : itemContainer.getContainers()) {
			IModule module = moduleContainer.getModule();
			if (module instanceof IStorageModule) {
				IStorageModule storageModule = (IStorageModule) module;
				if (storageModule.isValidForPosition(position, moduleContainer)) {
					return moduleContainer;
				}
			}
		}
		return null;
	}

	/**
	 * Create a module state, post a ModuleStateCreateEvent and load the state
	 * data from the item stack.
	 */
	public static IModuleProvider loadOrCreateModuleProvider(IModular modular, ItemStack stack) {
		IModuleItemProvider itemProvider = getProviderFromItem(modular, stack);
		IModuleItemContainer itemContainer = getContainerFromItem(stack, modular);
		IModuleProvider provider = new ModuleProvider(itemContainer, modular, itemProvider != null ? itemProvider.getItemStack() : stack);
		List<ModuleEntry> states = new LinkedList<>();
		if (itemProvider != null) {
			for (IModuleState moduleState : itemProvider) {
				states.add(new ModuleEntry(moduleState, true));
			}
			for (IModuleContainer container : itemContainer.getContainers()) {
				boolean mustBeAdded = true;
				for (ModuleEntry entry : states) {
					IModuleState moduleState = entry.moduleState;
					if (moduleState.getContainer() == container) {
						mustBeAdded = false;
						break;
					}
				}
				if (mustBeAdded) {
					states.add(new ModuleEntry(createModuleState(provider, itemContainer, container.getIndex()), false));
				}
			}
		} else {
			for (IModuleState moduleState : createModuleStates(provider, itemContainer)) {
				states.add(new ModuleEntry(moduleState, true));
			}
		}
		for (ModuleEntry entry : states) {
			if (entry.isNew) {
				IModuleState state = entry.moduleState;
				state = state.getModule().loadStateFromItem(state, stack);
				MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleStateLoadItemEvent(state, stack));
				entry.moduleState = state;
			}
		}
		for (ModuleEntry entry : states) {
			provider.addModuleState(entry.moduleState);
		}
		return provider;
	}

	private static class ModuleEntry {

		private IModuleState moduleState;
		private boolean isNew;

		public ModuleEntry(IModuleState moduleState, boolean isNew) {
			this.moduleState = moduleState;
			this.isNew = isNew;
		}
	}
}
