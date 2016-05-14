package de.nedelosk.forestmods.common.modules;

import java.util.List;

import de.nedelosk.forestmods.common.modular.assembler.AssemblerGroup;
import de.nedelosk.forestmods.common.modular.assembler.AssemblerSlot;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modular.IModularTileEntity;
import de.nedelosk.forestmods.library.modular.ModularHelper;
import de.nedelosk.forestmods.library.modular.assembler.IAssembler;
import de.nedelosk.forestmods.library.modular.assembler.IAssemblerGroup;
import de.nedelosk.forestmods.library.modular.assembler.IAssemblerSlot;
import de.nedelosk.forestmods.library.modules.IModuleContainer;
import de.nedelosk.forestmods.library.modules.ModuleManager;
import de.nedelosk.forestmods.library.modules.engine.IModuleEngine;
import de.nedelosk.forestmods.library.modules.storage.IModuleBattery;
import net.minecraft.item.ItemStack;

public abstract class ModuleMachineEngine extends ModuleMachine {

	protected final int engines;

	public ModuleMachineEngine(IModular modular, IModuleContainer container, int speedModifier, int engines) {
		super(modular, container, speedModifier);
		this.engines = engines;
	}

	@Override
	public IAssemblerGroup createGroup(IAssembler assembler, ItemStack stack, int groupID) {
		IAssemblerGroup group = new AssemblerGroup(assembler, groupID);
		IAssemblerSlot controllerSlot = new AssemblerSlot(group, 4, 4, assembler.getNextIndex(group), "controller", ModuleMachine.class);
		group.setControllerSlot(controllerSlot);
		if(engines > 0){
			IAssemblerSlot engine = group.addSlot(new AssemblerSlot(group, 4, 6, assembler.getNextIndex(group), "engine_0", IModuleEngine.class, controllerSlot));
			if(engines > 1){
				IAssemblerSlot engine_0 = group.addSlot(new AssemblerSlot(group, 2, 6, assembler.getNextIndex(group), "engine_1", IModuleEngine.class, engine));
				if(engines > 2){
					IAssemblerSlot engine_1 = group.addSlot(new AssemblerSlot(group, 6, 6, assembler.getNextIndex(group), "engine_2", IModuleEngine.class, engine));
				}
			}
		}
		return group;
	}

	@Override
	public boolean canAssembleGroup(IAssemblerGroup group) {
		IAssembler assembler = group.getAssembler();

		boolean hasBattery = false;
		for(IAssemblerGroup otherGroup : assembler.getGroups().values()){
			if(otherGroup != null){
				ItemStack stack = otherGroup.getControllerSlot().getStack();
				if(stack != null){
					IModuleContainer container = ModuleManager.moduleRegistry.getContainerFromItem(stack);
					if(IModuleBattery.class.isAssignableFrom(container.getModuleClass())){
						hasBattery = true;
						break;
					}
				}
			}
		}
		if(!hasBattery){
			return false;
		}
		for(IAssemblerSlot slot : group.getSlots()){
			if(slot != null && slot.getStack() != null){
				IModuleContainer container = ModuleManager.moduleRegistry.getContainerFromItem(slot.getStack());
				if(IModuleEngine.class.isAssignableFrom(container.getModuleClass())){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean canWork(){
		return modular.getTile().getEnergyStored(null) > 0;
	}
}
