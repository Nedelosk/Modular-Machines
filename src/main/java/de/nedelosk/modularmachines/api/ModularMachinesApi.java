package de.nedelosk.modularmachines.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.nedelosk.modularmachines.api.energy.HeatLevel;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerItem;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.ModuleEvents;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.items.IModuleProvider;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;

public class ModularMachinesApi {

	@CapabilityInject(IModularHandler.class)
	public static Capability<IModularHandler> MODULAR_HANDLER_CAPABILITY;

	@CapabilityInject(IModuleProvider.class)
	public static Capability<IModuleProvider> MODULE_PROVIDER_CAPABILITY;

	public static final IForgeRegistry<IModule> MODULES = GameRegistry.findRegistry(IModule.class);
	public static final IForgeRegistry<IModuleContainer> MODULE_CONTAINERS = GameRegistry.findRegistry(IModuleContainer.class);
	private static final List<HeatLevel> HEAT_LEVELS = new ArrayList<>();
	private static final List<IModuleContainer> modulesWithDefaultItem = new ArrayList<>();

	public static Item defaultModuleItem;
	public static final int DEFAULT_ALLOWED_COMPLEXITY = 12;

	public static final float COLD_TEMP = 20;
	public static final int STEAM_PER_UNIT_WATER = 160;
	public static final float BOILING_POINT = 100;

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
		IModuleState state = loadModuleState(modular, stack);
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

	/**
	 * Write a modular to a item stack.
	 */
	public static ItemStack saveModular(ItemStack modularItem, IModular modular, EntityPlayer player){
		IModularHandler modularHandler = modularItem.getCapability(ModularMachinesApi.MODULAR_HANDLER_CAPABILITY, null);
		modularItem = modularItem.copy();
		IModularHandlerItem<IModular, IModularAssembler, NBTTagCompound> itemHandler = (IModularHandlerItem) modularHandler;
		itemHandler.setModular(modular);
		itemHandler.setWorld(player.getEntityWorld());
		itemHandler.setOwner(player.getGameProfile());
		itemHandler.setUID();
		modularItem.setTagCompound(itemHandler.serializeNBT());
		return modularItem;
	}

	public static IModuleState loadModuleState(IModular modular, ItemStack stack){
		if(stack.hasCapability(ModularMachinesApi.MODULE_PROVIDER_CAPABILITY, null)){
			IModuleProvider provider = stack.getCapability(ModularMachinesApi.MODULE_PROVIDER_CAPABILITY, null);
			if(provider != null){
				return provider.createState(modular);
			}
		}
		return null;
	}

	/**
	 * Create a module state, post a ModuleStateCreateEvent and load the state data from the item stack. 
	 */
	public static IModuleState loadOrCreateModuleState(IModular modular, ItemStack stack) {
		IModuleState state = loadModuleState(modular, stack);
		if(state != null){
			return state;
		}
		IModuleContainer container = getContainerFromItem(stack, modular);
		if(container != null){
			state = container.getModule().createState(modular, container);
			MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleStateCreateEvent(state));
			state = state.build();
			if(stack != null &&  stack.getItem() != null){
				state = state.getModule().loadStateFromItem(state, stack);
				MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleStateLoadItemEvent(state, stack));
			}
			if(stack.hasCapability(ModularMachinesApi.MODULE_PROVIDER_CAPABILITY, null)){
				IModuleProvider provider = stack.getCapability(ModularMachinesApi.MODULE_PROVIDER_CAPABILITY, null);
				if(provider != null ){
					provider.setState(state);
				}
			}
		}
		return state;
	}

	/**
	 * Save a module state to the module provider capability in the item stack.
	 */
	public static ItemStack saveModuleState(IModuleState moduleState){
		ItemStack stack = moduleState.getModule().saveDataToItem(moduleState);
		IModuleProvider provider = stack.getCapability(ModularMachinesApi.MODULE_PROVIDER_CAPABILITY, null);
		if(moduleState != null && provider != null){
			if(!moduleState.getModule().isClean(moduleState)){
				provider.setState(moduleState);
			}
		}
		return stack;
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
		ItemStack itemStack = new ItemStack(defaultModuleItem);
		NBTTagCompound nbtTag = new NBTTagCompound();
		nbtTag.setString("Container", container.getRegistryName().toString());
		itemStack.setTagCompound(nbtTag);

		return itemStack;
	}

	public static List<IModuleContainer> getModulesWithDefaultItem() {
		return modulesWithDefaultItem;
	}

	static{
		registerType(new HeatLevel(COLD_TEMP, 0.1, 0.02));
		registerType(new HeatLevel(50, 0.085, 0.035));
		registerType(new HeatLevel(100, 0.075, 0.045));
		registerType(new HeatLevel(150, 0.065, 0.050));
		registerType(new HeatLevel(200, 0.035, 0.055));
		registerType(new HeatLevel(250, 0.030, 0.060));
		registerType(new HeatLevel(300, 0.025, 0.065));
		registerType(new HeatLevel(400, 0.020, 0.065));
		registerType(new HeatLevel(500, 0.015, 0.075));
		registerType(new HeatLevel(750, 0.005, 0.085));
	}

	public static void registerType(HeatLevel heatLevel){
		if(!HEAT_LEVELS.contains(heatLevel)){
			HEAT_LEVELS.add(heatLevel);
			Collections.sort(HEAT_LEVELS);
		}
	}

	public static int getHeatLevelIndex(HeatLevel level){
		return HEAT_LEVELS.indexOf(level);

	}

	public static HeatLevel getHeatLevel(double heat){
		for(int i = HEAT_LEVELS.size() - 1;i >= 0;i--){
			HeatLevel level = HEAT_LEVELS.get(i);
			if(level.getHeatMin() <= heat){
				return level;
			}
		}
		return null;
	}

}
