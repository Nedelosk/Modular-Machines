package de.nedelosk.modularmachines.common.modular;

import de.nedelosk.modularmachines.api.modular.IAssemblerLogic;
import de.nedelosk.modularmachines.api.modular.IAssemblerSlot;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.IModuleController;
import de.nedelosk.modularmachines.api.modules.IModuleDrive;
import de.nedelosk.modularmachines.api.modules.tool.IModuleTool;
import de.nedelosk.modularmachines.common.core.ModuleManager;
import net.minecraft.item.ItemStack;

public class AssemblerLogicBasic implements IAssemblerLogic {

	@Override
	public boolean isItemValid(ItemStack stack, IAssemblerSlot slot) {
		IModuleContainer container = ModularManager.getContainerFromItem(stack);
		if(container.getModule() instanceof IModuleController){
			if(slot.getModuleClass() == null){
				return false;
			}
		}else if(container.getModule() instanceof IModuleTool){
			if(slot.getModuleClass() == null){
				return false;
			}
		}else if(container.getModule() instanceof IModuleDrive){
			if(slot.getModuleClass() == null){
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean canChangeStatus(boolean isActive, IAssemblerSlot slot) {
		return true;
	}

	@Override
	public String getUID() {
		return "basic";
	}
}
