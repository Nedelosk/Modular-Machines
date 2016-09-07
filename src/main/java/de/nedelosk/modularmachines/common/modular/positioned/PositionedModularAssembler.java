package de.nedelosk.modularmachines.common.modular.positioned;

import java.util.EnumMap;
import java.util.Locale;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.IAssemblerLogic;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IPositionedModular;
import de.nedelosk.modularmachines.api.modular.IPositionedModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.EnumStoragePosition;
import de.nedelosk.modularmachines.api.modules.IModuleModuleStorage;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IPositionedModuleStorage;
import de.nedelosk.modularmachines.api.modules.storage.PositionedModuleStorage;
import de.nedelosk.modularmachines.common.inventory.ContainerPositionedAssembler;
import de.nedelosk.modularmachines.common.modular.ModularAssembler;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class PositionedModularAssembler extends ModularAssembler implements IPositionedModularAssembler {

	protected final EnumMap<EnumStoragePosition, IAssemblerLogic> logics = new EnumMap(EnumStoragePosition.class);
	protected EnumStoragePosition selectedPosition;

	public PositionedModularAssembler(IModularHandler modularHandler, NBTTagCompound nbtTag) {
		super(modularHandler, nbtTag);

		for(EnumStoragePosition pos : EnumStoragePosition.values()){
			logics.put(pos, new PositionedAssemblerLogic(this, pos));
		}
	}

	public PositionedModularAssembler(IModularHandler modularHandler, ItemStack[] moduleStacks) {
		super(modularHandler, moduleStacks);
		this.selectedPosition = EnumStoragePosition.INTERNAL;

		for(EnumStoragePosition pos : EnumStoragePosition.values()){
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
		selectedPosition = EnumStoragePosition.values()[nbt.getShort("selectedPosition")];
	}

	@Override
	public void setSelectedPosition(EnumStoragePosition position) {
		this.selectedPosition = position;
	}

	@Override
	public IModular assemble() throws AssemblerException {
		IPositionedModular modular = new PositionedModular();
		for(EnumStoragePosition position : EnumStoragePosition.values()){
			for(int index = position.startSlotIndex;index < position.endSlotIndex + 1;index++){
				ItemStack slotStack = assemblerHandler.getStackInSlot(index);
				if(slotStack != null){
					IModuleState state = ModularMachinesApi.loadOrCreateModuleState(modular, slotStack);
					IPositionedModuleStorage storage = modular.getModuleStorage(position);
					if(state != null && state.getModule() instanceof IModuleModuleStorage && storage == null){
						storage = new PositionedModuleStorage(modular, position);
						modular.setModuleStorage(position, storage);
					}
					if(state != null){
						if(storage != null){
							storage.addModule(slotStack, state);
						}else{
							throw new AssemblerException(Translator.translateToLocalFormatted("modular.assembler.no.storage", position.getLocName().toLowerCase(Locale.ENGLISH)));
						}
					}
				}
			}
		}
		for(EnumStoragePosition position : EnumStoragePosition.values()){
			IPositionedModuleStorage storage = modular.getModuleStorage(position);
			if(storage != null){
				for(IModuleState state : storage.getModules()){
					state.getModule().assembleModule(this, modular, storage, state);
				}
			}
		}
		for(EnumStoragePosition pos : EnumStoragePosition.values()){
			logics.get(pos).canAssemble(modular);
		}
		testComplexity();
		modular.onModularAssembled();
		return modular;
	}

	@Override
	public Container createContainer(IModularHandler tile, InventoryPlayer inventory) {
		return new ContainerPositionedAssembler(tile, inventory);
	}

	@Override
	public IAssemblerLogic getLogic(EnumStoragePosition pos) {
		return logics.get(pos);
	}

	@Override
	public EnumStoragePosition getSelectedPosition() {
		return selectedPosition;
	}

	@Override
	public IPositionedModularAssembler copy(IModularHandler handler) {
		return new PositionedModularAssembler(handler, assemblerHandler.getStacks());
	}
}
