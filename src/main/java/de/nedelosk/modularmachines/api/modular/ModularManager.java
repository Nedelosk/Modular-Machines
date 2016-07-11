package de.nedelosk.modularmachines.api.modular;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.IModuleState;
import de.nedelosk.modularmachines.api.modules.ModuleEvents;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.items.IItemHandler;

public class ModularManager {

	private static final Map<String, IAssemblerLogic> assemblerLogics = new HashMap<>();

	@CapabilityInject(IModularHandler.class)
	public static Capability<IModularHandler> MODULAR_HANDLER_CAPABILITY;

	/**
	 * 
	 * Assemble a modular from the items of a IItemHandler.
	 * @return Return a IModuleStorage, with all modules that are a added.
	 */
	public static IModuleStorage assembleModular(IItemHandler handler, EntityPlayer player, IModular modular, ItemStack modularItem){
		if(handler == null || player == null || modularItem == null){
			return null;
		}
		//Set modular output slot to null.
		//Test if casing item null.
		handler.extractItem(1, 1, false);
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
			Map<IModuleState, Integer> stateToSlotIndex = new HashMap<>();
			Map<Integer, IModuleState> slotIndexToState = new HashMap<>();
			IModuleIndexStorage storage = new IModuleIndexStorage() {
				@Override
				public Map<IModuleState, Integer> getStateToSlotIndex() {
					return stateToSlotIndex;
				}

				@Override
				public Map<Integer, IModuleState> getSlotIndexToState() {
					return slotIndexToState;
				}
			};
			for(int index = 0;index < handler.getSlots();index++){
				if(index != 1){
					ItemStack slotStack = handler.getStackInSlot(index);
					if(slotStack != null){
						IModuleContainer container = ModularManager.getContainerFromItem(slotStack);
						IModuleState state = modular.addModule(slotStack, createModuleState(modular, slotStack, container));
						if(state != null){
							stateToSlotIndex.put(state, Integer.valueOf(index));
							slotIndexToState.put(Integer.valueOf(index), state);
						}
					}
				}
			}
			for(IModuleState state : modular.getModuleStates()){
				if(state != null){
					if(!state.getModule().assembleModule(handler, modular, state, storage)){
						return modular;
					}
				}else{
					return modular;
				}
			}
			for(IAssemblerLogic logic : assemblerLogics.values()){
				if(!logic.canAssemble(modular)){
					return modular;
				}
			}

			modular.onAssembleModular();

			modularItem = modularItem.copy();

			if(modularItem.hasCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null)){
				IModularHandler modularHandler = modularItem.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
				if(modularHandler instanceof IModularHandlerItem){
					IModularHandlerItem<IModular, NBTTagCompound> itemHandler = (IModularHandlerItem) modularHandler;
					itemHandler.setModular(modular);
					itemHandler.setWorld(player.getEntityWorld());
					itemHandler.setOwner(player.getGameProfile());
					itemHandler.setUID();
					modularItem.setTagCompound(itemHandler.serializeNBT());
					if(handler.insertItem(1, modularItem.copy(), true) == null){
						handler.insertItem(1, modularItem.copy(), false);
						return modular;
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
		IModuleState createdState = moduleState.build();
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

	public static IModularHandler getModularHandler(ICapabilityProvider provider){
		if(provider == null){
			return null;
		}
		if(provider.hasCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null)){
			return provider.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
		}
		return null;	
	}

	public static void registerAssemblerLogic(IAssemblerLogic logic){
		if(logic == null){
			return;
		}
		if(!assemblerLogics.containsKey(logic.getUID())){
			assemblerLogics.put(logic.getUID(), logic);
		}
	}

	@Nonnull
	public static Map<String, IAssemblerLogic> getAssemblerLogics() {
		return Collections.unmodifiableMap(assemblerLogics);
	}

}
