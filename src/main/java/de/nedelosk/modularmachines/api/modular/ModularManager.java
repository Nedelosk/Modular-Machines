package de.nedelosk.modularmachines.api.modular;

import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.ModuleEvents;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.modular.Modular;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.items.IItemHandler;

public class ModularManager {

	@CapabilityInject(IModularHandler.class)
	public static Capability<IModularHandler> MODULAR_HANDLER_CAPABILITY;

	/**
	 * 
	 * Assemble a modular from the items of a IItemHandler.
	 */
	public static ItemStack assembleModular(IItemHandler handler, EntityPlayer player, ItemStack modularItem){
		//Set modular output slot to null.
		handler.insertItem(1, null, false);
		//Test if casing item null.
		if(handler.getStackInSlot(0) != null){
			boolean notNull = false;
			for(int index = 2;index < handler.getSlots();index++){
				if(handler.getStackInSlot(index) != null){
					notNull = true;
				}
			}
			if(!notNull){
				return null;
			}
			IModular modular = new Modular();
			for(int index = 0;index < handler.getSlots();index++){
				if(index != 1){
					ItemStack slotStack = handler.getStackInSlot(index);
					if(slotStack != null){
						IModuleContainer container = ModularManager.getContainerFromItem(slotStack);
						modular.addModule(slotStack, createModuleState(modular, slotStack, container));
					}
				}
			}
			for(IModuleState state : modular.getModuleStates()){
				if(state != null){
					if(!state.getModule().assembleModule(handler, modular, state)){
						return null;
					}
				}else{
					return null;
				}
			}
			modular.onAssembleModular();

			if(modularItem.hasCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null)){
				IModularHandler modularHandler = modularItem.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
				if(modularHandler instanceof IModularHandlerItem){
					IModularHandlerItem itemHandler = (IModularHandlerItem) modularHandler;
					itemHandler.setModular(modular);
					itemHandler.setWorld(player.getEntityWorld());
					itemHandler.setOwner(player.getGameProfile());
					itemHandler.setUID();
					if(handler.insertItem(1, modularItem.copy(), true) != null){
						return handler.insertItem(1, modularItem.copy(), false);
					}
				}
			}
		}
		return null;
	}

	/**
	 * Create a module state with, post a ModuleStateCreateEvent and load the state data from the item. 
	 */
	public static IModuleState createModuleState(IModular modular, ItemStack slotStack, IModuleContainer container){
		IModuleState moduleState = container.getModule().createState(modular, container);
		MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleStateCreateEvent(moduleState));
		IModuleState createdState = moduleState.createState();
		return createdState.getModule().loadStateFromItem(createdState, slotStack);
	}

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
