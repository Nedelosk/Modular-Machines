package de.nedelosk.modularmachines.api.modules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.api.material.IMetalMaterial;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.containers.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.containers.IModuleItemContainer;
import de.nedelosk.modularmachines.api.modules.containers.IModuleItemProvider;
import de.nedelosk.modularmachines.api.modules.containers.IModuleProvider;
import de.nedelosk.modularmachines.api.modules.containers.ModuleProvider;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IStorageModule;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;

public class ModuleManager {

	@CapabilityInject(IModuleItemProvider.class)
	public static Capability<IModuleItemProvider> MODULE_PROVIDER_CAPABILITY;

	public static final IForgeRegistry<IModule> MODULES = GameRegistry.findRegistry(IModule.class);
	public static final IForgeRegistry<IModuleItemContainer> MODULE_CONTAINERS = GameRegistry.findRegistry(IModuleItemContainer.class);
	public static final List<Item> moduleItems = new ArrayList();
	private static final List<IModuleItemContainer> modulesWithDefaultItem = new ArrayList<>();
	private static final Map<IMetalMaterial, ItemStack[]> materialsWithHolder = new HashMap<>();

	public static Item defaultModuleItem;
	public static Item defaultHolderItem;

	private ModuleManager() {
	}

	/**
	 * @return The matching module container for the stack.
	 */
	public static IModuleItemContainer getContainerFromItem(ItemStack stack){
		return getContainerFromItem(stack, null);
	}

	/**
	 * @return The matching module container for the stack.
	 */
	public static IModuleItemContainer getContainerFromItem(ItemStack stack, IModular modular){
		if (stack == null) {
			return null;
		}
		IModuleItemContainer itemContainer = getContainerFromItem(modular, stack);
		if(itemContainer != null){
			return itemContainer;
		}
		for(IModuleItemContainer container : MODULE_CONTAINERS) {
			if(container.matches(stack)){
				return container;
			}
		}
		return null;
	}

	public static NBTTagCompound writeStateToNBT(IModuleState moduleState){
		if(moduleState != null && moduleState.getContainer() != null){
			NBTTagCompound compoundTag = moduleState.serializeNBT();
			compoundTag.setInteger("Index", moduleState.getContainer().getIndex());
			MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleStateSaveEvent(moduleState, compoundTag));
			return compoundTag;
		}
		return null;
	}

	public static IModuleState loadStateFromNBT(IModuleProvider provider, IModuleItemContainer itemContainer, NBTTagCompound compoundTag){
		if(itemContainer != null){
			IModuleState state = ModuleManager.createModuleState(provider, itemContainer, compoundTag.getInteger("Index"));
			state.deserializeNBT(compoundTag);
			MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleStateLoadEvent(state, compoundTag));
			return state;
		}
		return null;
	}

	/**
	 * Save a module state to the module provider capability in the item stack.
	 */
	public static ItemStack saveModuleStateToItem(IModuleProvider provider){
		ItemStack stack = provider.getItemStack().copy();
		if(provider != null){
			for(IModuleState state : provider.getModuleStates()){
				if(state != null){
					state.getModule().saveDataToItem(stack, state);
				}
			}
		}
		IModuleItemProvider itemProvider = stack.getCapability(ModuleManager.MODULE_PROVIDER_CAPABILITY, null);
		if(itemProvider != null && provider != null){
			List<IModuleState> states = new ArrayList<>();
			for(IModuleState state : provider.getModuleStates()){
				if(!state.getModule().isClean(state)){
					states.add(state);
				}
			}
			if(!states.isEmpty()){
				itemProvider.setStates(states);
			}
		}
		return stack;
	}

	public static IModuleItemContainer getContainerFromItem(IModular modular, ItemStack stack){
		if(stack != null && stack.hasCapability(ModuleManager.MODULE_PROVIDER_CAPABILITY, null)){
			IModuleItemProvider provider = stack.getCapability(ModuleManager.MODULE_PROVIDER_CAPABILITY, null);
			if(provider != null){
				return provider.getContainer();
			}
		}
		return null;
	}

	public static IModuleItemProvider loadModuleStateFromItem(IModular modular, ItemStack stack){
		if(stack != null && stack.hasCapability(ModuleManager.MODULE_PROVIDER_CAPABILITY, null)){
			return stack.getCapability(ModuleManager.MODULE_PROVIDER_CAPABILITY, null);
		}
		return null;
	}

	public static IModuleState createModuleState(IModuleProvider provider, IModuleItemContainer itemContainer, int index){
		if(itemContainer != null){
			IModuleContainer container = itemContainer.getContainer(index);
			if(container != null){
				IModuleState state = container.getModule().createState(provider, container);
				if(state != null){
					MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleStateCreateEvent(state));
					return state.build();
				}
			}
		}
		return null;
	}

	public static List<IModuleState> createModuleState(IModuleProvider provider, IModuleItemContainer itemContainer){
		if(itemContainer != null){
			List<IModuleState> moduleStates = new ArrayList<>();
			for(IModuleContainer container : itemContainer.getContainers()){
				if(container != null){
					IModuleState state = container.getModule().createState(provider, container);
					if(state != null){
						MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleStateCreateEvent(state));
						moduleStates.add(state.build());
					}
				}
			}
			return moduleStates;
		}
		return Collections.emptyList();
	}

	public static IModuleState<IStorageModule> getStorageState(IModuleProvider provider, IStoragePosition position){
		for(IModuleState moduleState : provider.getModuleStates()){
			IModule module = moduleState.getModule();
			if(module instanceof IStorageModule){
				IStorageModule storageModule = (IStorageModule) module;
				if(storageModule.isValidForPosition(position, moduleState.getContainer())){
					return moduleState;
				}
			}
		}
		return null;
	}

	public static IModuleContainer<IStorageModule, IModuleProperties> getStorageContainer(IModuleItemContainer itemContainer, IStoragePosition position){
		if(itemContainer == null){
			return null;
		}
		for(IModuleContainer moduleContainer : itemContainer.getContainers()){
			IModule module = moduleContainer.getModule();
			if(module instanceof IStorageModule){
				IStorageModule storageModule = (IStorageModule) module;
				if(storageModule.isValidForPosition(position, moduleContainer)){
					return moduleContainer;
				}
			}
		}
		return null;
	}

	/**
	 * Create a module state, post a ModuleStateCreateEvent and load the state data from the item stack. 
	 */
	public static IModuleProvider loadOrCreateModuleProvider(IModular modular, ItemStack stack) {
		IModuleItemProvider itemProvider = loadModuleStateFromItem(modular, stack);
		IModuleItemContainer itemContainer = getContainerFromItem(stack, modular);
		IModuleProvider provider = new ModuleProvider(itemContainer, modular, stack.copy(), new ArrayList<>());
		Map<Boolean, IModuleState> moduleStates = new HashMap<>();
		if(itemProvider != null){
			for(IModuleState moduleState : itemProvider.createStates(provider)){
				moduleStates.put(true, moduleState);
			}
			CONTAINERS: for(IModuleContainer container : itemContainer.getContainers()){
				for(IModuleState moduleState : moduleStates.values()){
					if(moduleState.getContainer() == container){
						continue CONTAINERS;
					}
				}
				moduleStates.put(false, createModuleState(provider, itemContainer, container.getIndex()));
			}
		}else{
			for(IModuleState moduleState : createModuleState(provider, itemContainer)){
				moduleStates.put(false, moduleState);
			}
		}
		for(Entry<Boolean, IModuleState> stateEntry : moduleStates.entrySet()){
			if(stateEntry.getKey()){
				IModuleState state = stateEntry.getValue();
				if(state != null){
					state = state.getModule().loadStateFromItem(state, stack);
					MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleStateLoadItemEvent(state, stack));
				}
			}
		}
		provider.getModuleStates().addAll(moduleStates.values());
		return provider;
	}

	public static List<IModuleState> getModulesWithPages(IModular modular){
		List<IModuleState> modulesWithPages = Lists.newArrayList();
		for(IModuleState moduleState : modular.getModules()) {
			if (moduleState != null && !moduleState.getPages().isEmpty()) {
				modulesWithPages.add(moduleState);
			}
		}
		return modulesWithPages;
	}

	public static boolean hasDefaultStack(IModuleItemContainer container){
		return modulesWithDefaultItem.contains(container);
	}

	public static ItemStack createDefaultStack(IModuleItemContainer container) {
		if(container == null){
			return null;
		}
		if(defaultModuleItem == null){
			return null;
		}
		if(!modulesWithDefaultItem.contains(container)){
			modulesWithDefaultItem.add(container);
		}
		if(container.getMaterial() instanceof IMetalMaterial){
			addMaterial((IMetalMaterial) container.getMaterial());
		}
		ItemStack itemStack = new ItemStack(defaultModuleItem);
		NBTTagCompound nbtTag = new NBTTagCompound();
		nbtTag.setString("Material", container.getMaterial().getName());
		nbtTag.setString("Size", container.getSize().getName());
		String moduleName = "";
		for(IModuleContainer moduleContainer : container.getContainers()){
			moduleName+= "."+moduleContainer.getDisplayName();
		}
		nbtTag.setString("ModuleName", moduleName.replaceFirst(".", ""));
		itemStack.setTagCompound(nbtTag);

		return itemStack;
	}

	public static ItemStack getHolder(IMetalMaterial material, int meta){
		if(defaultHolderItem == null){
			return null;
		}
		if(material != null){
			if(!materialsWithHolder.containsKey(material)){
				addMaterial(material);
			}
			return materialsWithHolder.get(material)[meta];
		}
		return null;
	}

	public static void addMaterial(IMetalMaterial material){
		if(defaultHolderItem == null){
			return;
		}
		if(material != null){
			if(!materialsWithHolder.containsKey(material)){
				ItemStack[] holders = new ItemStack[3];
				for(int i = 0;i < 3;i++){
					ItemStack stack = new ItemStack(defaultHolderItem, 1, i);
					NBTTagCompound nbtTag = new NBTTagCompound();
					nbtTag.setString("Material",  material.getName());
					stack.setTagCompound(nbtTag);
					holders[i] = stack;
				}
				materialsWithHolder.put(material, holders);
			}
		}
	}

	public static List<IModuleItemContainer> getModulesWithDefaultItem() {
		return modulesWithDefaultItem;
	}


	public static Collection<IMetalMaterial> getMaterialsWithHolder() {
		return materialsWithHolder.keySet();
	}

}
