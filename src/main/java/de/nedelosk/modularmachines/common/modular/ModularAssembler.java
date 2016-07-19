package de.nedelosk.modularmachines.common.modular;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import de.nedelosk.modularmachines.api.modular.IAssemblerLogic;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.IModuleIndexStorage;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import de.nedelosk.modularmachines.common.modular.assembler.AssemblerItemHandler;
import de.nedelosk.modularmachines.common.modular.assembler.AssemblerLogic;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSelectAssemblerPosition;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ModularAssembler implements IModularAssembler {

	protected final AssemblerItemHandler assemblerHandler;
	protected final IModularHandler modularHandler;
	protected final EnumMap<EnumPosition, IAssemblerLogic> logics = new EnumMap(EnumPosition.class);
	protected EnumPosition selectedPosition;

	public ModularAssembler(IModularHandler modularHandler, NBTTagCompound nbtTag) {
		this.modularHandler = modularHandler;
		this.selectedPosition = EnumPosition.INTERNAL;
		this.assemblerHandler = new AssemblerItemHandler();
		this.assemblerHandler.deserializeNBT(nbtTag);

		for(EnumPosition pos : EnumPosition.values()){
			logics.put(pos, new AssemblerLogic(this, pos));
		}
	}

	public ModularAssembler(IModularHandler modularHandler, ItemStack[] moduleStacks) {
		this.modularHandler = modularHandler;
		this.selectedPosition = EnumPosition.INTERNAL;
		this.assemblerHandler = new AssemblerItemHandler(moduleStacks);

		for(EnumPosition pos : EnumPosition.values()){
			logics.put(pos, new AssemblerLogic(this, pos));
		}
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setShort("selectedPosition", Integer.valueOf(selectedPosition.ordinal()).shortValue());
		nbt.setTag("assemblerHandler", CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().writeNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, assemblerHandler, null));
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		selectedPosition = EnumPosition.values()[nbt.getShort("selectedPosition")];
		CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().readNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, assemblerHandler, null, nbt.getTag("assemblerHandler"));
	}

	@Override
	public void setSelectedPosition(EnumPosition position) {
		this.selectedPosition = position;
		if(modularHandler != null && modularHandler.getWorld() != null){
			if (modularHandler.getWorld().isRemote) {
				PacketHandler.INSTANCE.sendToServer(new PacketSelectAssemblerPosition(modularHandler, position));
			}
		}
	}

	@Override
	public IModular assemble() {
		IModular modular = new Modular();
		Map<IModuleState, Integer> stateToSlotIndex = new HashMap<>();
		Map<IModuleState, EnumPosition> stateToPos = new HashMap<>();
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
		for(int index = 0;index < assemblerHandler.getSlots();index++){
			if(index != 1){
				ItemStack slotStack = assemblerHandler.getStackInSlot(index);
				if(slotStack != null){
					IModuleContainer container = ModularManager.getContainerFromItem(slotStack);
					IModuleState state = modular.addModule(slotStack, ModularManager.loadModuleState(modular, slotStack, container));
					if(state != null){
						stateToSlotIndex.put(state, Integer.valueOf(index));
						slotIndexToState.put(Integer.valueOf(index), state);
					}
				}
			}
		}
		for(IModuleState state : modular.getModuleStates()){
			if(state != null){
				if(!state.getModule().assembleModule(this, modular, state, storage)){
					return null;
				}
			}else{
				return null;
			}
		}
		for(EnumPosition pos : EnumPosition.values()){
			if(!logics.get(pos).canAssemble(modular)){
				return null;
			}
		}
		return modular;
	}

	@Override
	public IAssemblerLogic getLogic(EnumPosition pos) {
		return logics.get(pos);
	}

	@Override
	public IModularHandler getHandler() {
		return modularHandler;
	}

	@Override
	public EnumPosition getSelectedPosition() {
		return selectedPosition;
	}

	@Override
	public IItemHandler getAssemblerHandler() {
		return assemblerHandler;
	}
}
