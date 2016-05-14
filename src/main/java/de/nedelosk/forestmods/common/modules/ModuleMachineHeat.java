package de.nedelosk.forestmods.common.modules;

import java.util.List;

import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modular.IModularTileEntity;
import de.nedelosk.forestmods.library.modular.ModularHelper;
import de.nedelosk.forestmods.library.modular.assembler.IAssembler;
import de.nedelosk.forestmods.library.modular.assembler.IAssemblerGroup;
import de.nedelosk.forestmods.library.modular.assembler.IAssemblerSlot;
import de.nedelosk.forestmods.library.modules.IModuleContainer;
import de.nedelosk.forestmods.library.modules.ModuleManager;
import de.nedelosk.forestmods.library.modules.casing.IModuleCasing;
import de.nedelosk.forestmods.library.modules.engine.IModuleEngine;
import de.nedelosk.forestmods.library.modules.heater.IModuleHeater;
import de.nedelosk.forestmods.library.modules.storage.IModuleBattery;
import net.minecraft.item.ItemStack;

public abstract class ModuleMachineHeat extends ModuleMachine {

	public ModuleMachineHeat(IModular modular, IModuleContainer container, int speedModifier) {
		super(modular, container, speedModifier);
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
				if(IModuleHeater.class.isAssignableFrom(container.getModuleClass())){
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean canWork(){
		IModuleCasing casing = ModularHelper.getCasing(modular);
		return casing.getHeat() > 0;
	}

}
