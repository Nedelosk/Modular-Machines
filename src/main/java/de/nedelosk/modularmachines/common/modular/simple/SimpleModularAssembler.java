package de.nedelosk.modularmachines.common.modular.simple;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.IAssemblerLogic;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.ISimpleModular;
import de.nedelosk.modularmachines.api.modular.ISimpleModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.inventory.ContainerSimpleAssembler;
import de.nedelosk.modularmachines.common.modular.ModularAssembler;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class SimpleModularAssembler extends ModularAssembler implements ISimpleModularAssembler {

	public IAssemblerLogic logic;

	public SimpleModularAssembler(IModularHandler modularHandler, ItemStack[] moduleStacks, IAssemblerLogic logic) {
		super(modularHandler, moduleStacks);

		this.logic = logic;
	}

	public SimpleModularAssembler(IModularHandler modularHandler, NBTTagCompound nbtTag, IAssemblerLogic logic) {
		super(modularHandler, nbtTag);

		this.logic = logic;
	}

	@Override
	public IModular assemble() throws AssemblerException {
		ISimpleModular modular = new SimpleModular(logic);
		for(int index = 0;index < 4;index++){
			ItemStack slotStack = assemblerHandler.getStackInSlot(index);
			if(slotStack != null){
				IModuleContainer container = ModularMachinesApi.getContainerFromItem(slotStack);
				modular.addModule(slotStack, ModularMachinesApi.loadModuleState(modular, slotStack, container));
			}
		}
		for(IModuleState state : modular.getModules()){
			state.getModule().assembleModule(this, modular, modular, state);
		}
		logic.canAssemble(modular);
		modular.assembleModular();
		return modular;
	}

	@Override
	public Container createContainer(IModularHandler tile, InventoryPlayer inventory) {
		return new ContainerSimpleAssembler(tile, inventory);
	}

	@Override
	public IAssemblerLogic getLogic() {
		return logic;
	}

	@Override
	public ISimpleModularAssembler copy(IModularHandler handler) {
		return new SimpleModularAssembler(handler, serializeNBT(), logic);
	}
}
