package de.nedelosk.modularmachines.common.modular;

import java.util.List;

import de.nedelosk.modularmachines.api.modular.IAssemblerLogic;
import de.nedelosk.modularmachines.api.modular.IAssemblerSlot;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModuleStorage;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.IModuleController;
import de.nedelosk.modularmachines.api.modules.IModuleDrive;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.tool.IModuleMachine;
import de.nedelosk.modularmachines.api.modules.tool.IModuleTool;
import net.minecraft.item.ItemStack;

public class AssemblerLogicBasic implements IAssemblerLogic {

	@Override
	public boolean isItemValid(ItemStack stack, IAssemblerSlot slot, IModuleStorage storage) {
		IModuleContainer container = ModularManager.getContainerFromItem(stack);
		if(container.getModule() instanceof IModuleController){
			if(slot.getModuleClass() == null){
				return false;
			}
		}else if(container.getModule() instanceof IModuleMachine){
			if(slot.getModuleClass() == null){
				return false;
			}
			if(storage != null){
				List<IModuleState<IModuleTool>> tools = storage.getModules(IModuleTool.class);
				if(!tools.isEmpty()){
					int currentSize = 0;
					for(IModuleState<IModuleTool> tool : tools){
						currentSize += tool.getModule().getSize();
					}
					currentSize += ((IModuleTool)container.getModule()).getSize();
					if(currentSize > 3){
						return false;
					}
				}
			}
		}else if(container.getModule() instanceof IModuleDrive){
			if(slot.getModuleClass() == null){
				return false;
			}
			if(storage != null){
				List<IModuleState<IModuleDrive>> drives = storage.getModules(IModuleDrive.class);
				if(!drives.isEmpty()){
					int currentSize = 0;
					for(IModuleState<IModuleDrive> drive : drives){
						currentSize += drive.getModule().getSize();
					}
					currentSize += ((IModuleDrive)container.getModule()).getSize();
					if(currentSize > 3){
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean canAssemble(IModular modular) {
		if(modular.getModules(IModuleController.class).isEmpty()){
			return false;
		}
		return true;
	}

	@Override
	public boolean canChangeStatus(boolean isActive, IAssemblerSlot slot, IModuleStorage storage) {
		return true;
	}

	@Override
	public String getUID() {
		return "basic";
	}
}
