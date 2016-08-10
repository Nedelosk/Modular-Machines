package de.nedelosk.modularmachines.common.modular.positioned;

import java.util.EnumMap;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.IAssemblerLogic;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IPositionedModular;
import de.nedelosk.modularmachines.api.modular.IPositionedModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IPositionedModuleStorage;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import de.nedelosk.modularmachines.api.modules.storaged.IModuleModuleStorage;
import de.nedelosk.modularmachines.common.inventory.ContainerPositionedAssembler;
import de.nedelosk.modularmachines.common.modular.ModularAssembler;
import de.nedelosk.modularmachines.common.modules.storage.PositionedModuleStorage;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSelectAssemblerPosition;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class PositionedModularAssembler extends ModularAssembler implements IPositionedModularAssembler {

	protected final EnumMap<EnumPosition, IAssemblerLogic> logics = new EnumMap(EnumPosition.class);
	protected EnumPosition selectedPosition;

	public PositionedModularAssembler(IModularHandler modularHandler, NBTTagCompound nbtTag) {
		super(modularHandler, nbtTag);

		for(EnumPosition pos : EnumPosition.values()){
			logics.put(pos, new PositionedAssemblerLogic(this, pos));
		}
	}

	public PositionedModularAssembler(IModularHandler modularHandler, ItemStack[] moduleStacks) {
		super(modularHandler, moduleStacks);
		this.selectedPosition = EnumPosition.INTERNAL;

		for(EnumPosition pos : EnumPosition.values()){
			logics.put(pos, new PositionedAssemblerLogic(this, pos));
		}
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = super.serializeNBT();
		nbt.setShort("selectedPosition", Integer.valueOf(selectedPosition.ordinal()).shortValue());
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		super.deserializeNBT(nbt);
		selectedPosition = EnumPosition.values()[nbt.getShort("selectedPosition")];
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
	public IModular assemble() throws AssemblerException {
		IPositionedModular modular = new PositionedModular();
		for(EnumPosition position : EnumPosition.values()){
			for(int index = position.startSlotIndex;index < position.endSlotIndex + 1;index++){
				ItemStack slotStack = assemblerHandler.getStackInSlot(index);
				if(slotStack != null){
					IModuleState state = ModularMachinesApi.loadOrCreateModuleState(modular, slotStack);
					IPositionedModuleStorage storage = modular.getModuleStorage(position);
					if(state.getModule() instanceof IModuleModuleStorage && storage == null){
						storage = new PositionedModuleStorage(modular, position);
						modular.setModuleStorage(position, storage);
					}
					if(storage != null){
						storage.addModule(slotStack, state);
					}else{
						throw new AssemblerException(Translator.translateToLocal("modular.assembler.error.no.storage." + position.getName()));
					}
				}
			}
		}
		for(EnumPosition position : EnumPosition.values()){
			IPositionedModuleStorage storage = modular.getModuleStorage(position);
			if(storage != null){
				for(IModuleState state : storage.getModules()){
					state.getModule().assembleModule(this, modular, storage, state);
				}
			}
		}
		for(EnumPosition pos : EnumPosition.values()){
			logics.get(pos).canAssemble(modular);
		}
		modular.assembleModular();
		return modular;
	}

	@Override
	public Container createContainer(IModularHandler tile, InventoryPlayer inventory) {
		return new ContainerPositionedAssembler(tile, inventory);
	}

	@Override
	public IAssemblerLogic getLogic(EnumPosition pos) {
		return logics.get(pos);
	}

	@Override
	public EnumPosition getSelectedPosition() {
		return selectedPosition;
	}

	@Override
	public IPositionedModularAssembler copy(IModularHandler handler) {
		return new PositionedModularAssembler(handler, assemblerHandler.getStacks());
	}
}
