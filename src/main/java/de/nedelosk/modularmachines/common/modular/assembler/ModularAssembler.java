package de.nedelosk.modularmachines.common.modular.assembler;

import java.util.EnumMap;

import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.IAssemblerLogic;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IPositionedModuleStorage;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import de.nedelosk.modularmachines.api.modules.storaged.IModuleModuleStorage;
import de.nedelosk.modularmachines.client.gui.GuiAssembler;
import de.nedelosk.modularmachines.common.inventory.ContainerAssembler;
import de.nedelosk.modularmachines.common.modular.Modular;
import de.nedelosk.modularmachines.common.modules.storage.PositionedModuleStorage;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSelectAssemblerPosition;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
		deserializeNBT(nbtTag);

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
		nbt.setTag("assemblerHandler", assemblerHandler.serializeNBT());
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		selectedPosition = EnumPosition.values()[nbt.getShort("selectedPosition")];
		assemblerHandler.deserializeNBT(nbt.getCompoundTag("assemblerHandler"));
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
		IModular modular = new Modular();
		for(EnumPosition position : EnumPosition.values()){
			for(int index = position.startSlotIndex;index < position.endSlotIndex + 1;index++){
				ItemStack slotStack = assemblerHandler.getStackInSlot(index);
				if(slotStack != null){
					IModuleContainer container = ModularManager.getContainerFromItem(slotStack);
					IPositionedModuleStorage storage = modular.getModuleStorage(position);
					if(container.getModule() instanceof IModuleModuleStorage && storage == null){
						storage = new PositionedModuleStorage(modular, position);
						modular.setModuleStorage(position, storage);
					}
					storage.addModule(slotStack, ModularManager.loadModuleState(modular, slotStack, container));
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
	public IAssemblerLogic getLogic(EnumPosition pos) {
		return logics.get(pos);
	}

	@Override
	public IModularHandler getHandler() {
		return modularHandler;
	}

	@Override
	public Container createContainer(IModularHandler tile, InventoryPlayer inventory) {
		return new ContainerAssembler(tile, inventory);
	}

	@Override
	public GuiContainer createGui(IModularHandler tile, InventoryPlayer inventory) {
		return new GuiAssembler(tile, inventory);
	}

	@Override
	public EnumPosition getSelectedPosition() {
		return selectedPosition;
	}

	@Override
	public IItemHandler getAssemblerHandler() {
		return assemblerHandler;
	}

	@Override
	public IModularAssembler copy(IModularHandler handler) {
		return new ModularAssembler(handler, assemblerHandler.getStacks());
	}
}
