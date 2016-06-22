package de.nedelosk.modularmachines.common.modules;

import de.nedelosk.modularmachines.api.modular.ModularHelper;
import de.nedelosk.modularmachines.api.modular.assembler.IAssembler;
import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerGroup;
import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerSlot;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.api.modules.casing.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.heater.IModuleHeater;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IModuleBattery;
import net.minecraft.item.ItemStack;

public abstract class ModuleToolHeat extends ModuleTool {

	public ModuleToolHeat(int speedModifier) {
		super(speedModifier);
	}

	@Override
	public boolean canAssembleGroup(IAssemblerGroup group) {
		IAssembler assembler = group.getAssembler();

		boolean hasBattery = false;
		for(IAssemblerGroup otherGroup : assembler.getGroups().values()){
			if(otherGroup != null){
				ItemStack stack = otherGroup.getControllerSlot().getStack();
				if(stack != null){
					IModuleContainer container = ModuleManager.getContainerFromItem(stack);
					if(IModuleBattery.class.isAssignableFrom(container.getModule().getClass())){
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
				IModuleContainer container = ModuleManager.getContainerFromItem(slot.getStack());
				if(IModuleHeater.class.isAssignableFrom(container.getModule().getClass())){
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean canWork(IModuleState state){
		IModuleState<IModuleCasing> casingState = ModularHelper.getCasing(state.getModular());
		return casingState.getModule().getHeat(casingState) > 0;
	}

}
