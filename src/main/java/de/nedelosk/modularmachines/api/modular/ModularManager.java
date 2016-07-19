package de.nedelosk.modularmachines.api.modular;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerItem;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.ModuleEvents;
import de.nedelosk.modularmachines.api.modules.items.IModuleProvider;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;

public class ModularManager {

	@CapabilityInject(IModularHandler.class)
	public static Capability<IModularHandler> MODULAR_HANDLER_CAPABILITY;

	@CapabilityInject(IModuleProvider.class)
	public static Capability<IModuleProvider> MODULE_PROVIDER_CAPABILITY;

	/**
	 * Write a modular to a item stack.
	 */
	public static ItemStack writeModularToItem(ItemStack modularItem, IModular modular, EntityPlayer player){
		IModularHandler modularHandler = modularItem.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
		modularItem = modularItem.copy();
		IModularHandlerItem<IModular, NBTTagCompound> itemHandler = (IModularHandlerItem) modularHandler;
		itemHandler.setModular(modular);
		itemHandler.setWorld(player.getEntityWorld());
		itemHandler.setOwner(player.getGameProfile());
		itemHandler.setUID();
		modularItem.setTagCompound(itemHandler.serializeNBT());
		return modularItem;
	}

	/**
	 * Create a module state, post a ModuleStateCreateEvent and load the state data from the item. 
	 */
	public static IModuleState loadModuleState(IModular modular, ItemStack stack, IModuleContainer container){
		IModuleProvider provider = stack.getCapability(ModularManager.MODULE_PROVIDER_CAPABILITY, null);
		IModuleState moduleState = container.getModule().createState(modular, container);
		if(provider.getState() != null){
			moduleState.deserializeNBT(provider.getState().serializeNBT());
		}
		MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleStateCreateEvent(moduleState));
		IModuleState createdState = moduleState.build();
		if(stack != null &&  stack.getItem() != null){
			return createdState.getModule().loadStateFromItem(createdState, stack);
		}
		return createdState;
	}

	public static ItemStack saveModuleState(IModuleState moduleState){
		ItemStack stack = moduleState.getModule().saveDataToItem(moduleState);
		IModuleProvider provider = stack.getCapability(ModularManager.MODULE_PROVIDER_CAPABILITY, null);
		if(moduleState != null){
			provider.setState(moduleState);
		}
		return stack;
	}

	/**
	 * @return The matching module container for the stack.
	 */
	public static IModuleContainer getContainerFromItem(ItemStack stack){
		if (stack == null) {
			return null;
		}
		IForgeRegistry<IModuleContainer> containerRegistry = GameRegistry.findRegistry(IModuleContainer.class);
		for(IModuleContainer container : containerRegistry) {
			if (container.getItemStack() != null && container.getItemStack().getItem() != null && stack.getItem() == container.getItemStack().getItem()
					&& stack.getItemDamage() == container.getItemStack().getItemDamage()) {
				if (container.ignorNBT() || ItemStack.areItemStackTagsEqual(stack, container.getItemStack())) {
					return container;
				}
			}
		}
		return null;
	}

}
