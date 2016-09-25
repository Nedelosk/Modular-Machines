package de.nedelosk.modularmachines.api.modules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.api.material.IMetalMaterial;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.items.IModuleProvider;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;

public class ModuleManager {

	@CapabilityInject(IModuleProvider.class)
	public static Capability<IModuleProvider> MODULE_PROVIDER_CAPABILITY;

	public static final IForgeRegistry<IModule> MODULES = GameRegistry.findRegistry(IModule.class);
	public static final IForgeRegistry<IModuleContainer> MODULE_CONTAINERS = GameRegistry.findRegistry(IModuleContainer.class);
	private static final List<IModuleContainer> modulesWithDefaultItem = new ArrayList<>();
	private static final Map<IMetalMaterial, ItemStack[]> materialsWithHolder = new HashMap<>();

	public static Item defaultModuleItem;
	public static Item defaultHolderItem;

	private ModuleManager() {
	}

	/**
	 * @return The matching module container for the stack.
	 */
	public static IModuleContainer getContainerFromItem(ItemStack stack){
		return getContainerFromItem(stack, null);
	}

	/**
	 * @return The matching module container for the stack.
	 */
	public static IModuleContainer getContainerFromItem(ItemStack stack, IModular modular){
		if (stack == null) {
			return null;
		}
		IModuleState state = loadModuleStateFromItem(modular, stack);
		if(state != null){
			return state.getContainer();
		}
		for(IModuleContainer container : MODULE_CONTAINERS) {
			if(container.matches(stack)){
				return container;
			}
		}
		return null;
	}

	public static NBTTagCompound writeStateToNBT(IModular modular, IModuleState moduleState){
		if(moduleState != null && moduleState.getContainer() != null){
			NBTTagCompound compoundTag = moduleState.serializeNBT();
			compoundTag.setString("Container", moduleState.getContainer().getRegistryName().toString());
			MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleStateSaveEvent(moduleState, compoundTag));
			return compoundTag;
		}
		return null;
	}

	public static IModuleState loadStateFromNBT(IModular modular, NBTTagCompound compoundTag){
		ResourceLocation loc = new ResourceLocation(compoundTag.getString("Container"));
		IModuleContainer container = MODULE_CONTAINERS.getValue(loc);
		if(container != null){
			IModuleState state = ModuleManager.createModuleState(modular, container);
			state.deserializeNBT(compoundTag);
			MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleStateLoadEvent(state, compoundTag));
			return state;
		}
		return null;
	}

	/**
	 * Save a module state to the module provider capability in the item stack.
	 */
	public static ItemStack saveModuleStateToItem(IModuleState moduleState){
		ItemStack stack = moduleState.getModule().saveDataToItem(moduleState);
		IModuleProvider provider = stack.getCapability(ModuleManager.MODULE_PROVIDER_CAPABILITY, null);
		if(moduleState != null && provider != null){
			if(!moduleState.getModule().isClean(moduleState)){
				provider.setState(moduleState);
			}
		}
		return stack;
	}

	public static IModuleState loadModuleStateFromItem(IModular modular, ItemStack stack){
		if(stack != null && stack.hasCapability(ModuleManager.MODULE_PROVIDER_CAPABILITY, null)){
			IModuleProvider provider = stack.getCapability(ModuleManager.MODULE_PROVIDER_CAPABILITY, null);
			if(provider != null){
				return provider.createState(modular);
			}
		}
		return null;
	}

	public static IModuleState createModuleState(IModular modular, IModuleContainer container){
		IModuleState state = null;
		if(container != null){
			state = container.getModule().createState(modular, container);
			MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleStateCreateEvent(state));
			state = state.build();
			return state;
		}
		return null;
	}

	/**
	 * Create a module state, post a ModuleStateCreateEvent and load the state data from the item stack. 
	 */
	public static IModuleState loadOrCreateModuleState(IModular modular, ItemStack stack) {
		IModuleState state = loadModuleStateFromItem(modular, stack);
		if(state != null){
			return state;
		}else{
			state = createModuleState(modular, getContainerFromItem(stack, modular));
		}
		if(state != null){
			state = state.getModule().loadStateFromItem(state, stack);
			MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleStateLoadItemEvent(state, stack));
		}
		/*if(stack.hasCapability(ModularMachinesApi.MODULE_PROVIDER_CAPABILITY, null)){
			IModuleProvider provider = stack.getCapability(ModularMachinesApi.MODULE_PROVIDER_CAPABILITY, null);
			if(provider != null ){
				provider.setState(state);
			}
		}*/
		return state;
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

	public static boolean hasDefaultStack(IModuleContainer container){
		return modulesWithDefaultItem.contains(container);
	}

	public static ItemStack createDefaultStack(IModuleContainer container) {
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
		nbtTag.setString("Container", container.getRegistryName().toString());
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

	public static List<IModuleContainer> getModulesWithDefaultItem() {
		return modulesWithDefaultItem;
	}


	public static Collection<IMetalMaterial> getMaterialsWithHolder() {
		return materialsWithHolder.keySet();
	}

}
