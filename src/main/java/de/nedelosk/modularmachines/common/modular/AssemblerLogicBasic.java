package de.nedelosk.modularmachines.common.modular;

import java.util.List;

import de.nedelosk.modularmachines.api.modular.IAssemblerLogic;
import de.nedelosk.modularmachines.api.modular.IAssemblerSlot;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModuleStorage;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modules.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.IModuleController;
import de.nedelosk.modularmachines.api.modules.IModuleDrawer;
import de.nedelosk.modularmachines.api.modules.IModuleDrive;
import de.nedelosk.modularmachines.api.modules.IModuleState;
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
		}else if(container.getModule() instanceof IModuleTool){
			if(slot.getModuleClass() == null){
				return false;
			}
			if(storage != null){
				List<IModuleState<IModuleTool>> tools = storage.getModules(IModuleTool.class);
				if(!tools.isEmpty()){
					EnumModuleSize currentSize = null;
					for(IModuleState<IModuleTool> tool : tools){
						currentSize = EnumModuleSize.getNewSize(currentSize, tool.getModule().getSize());
					}
					currentSize = EnumModuleSize.getNewSize(currentSize, ((IModuleTool)container.getModule()).getSize());
					if(currentSize == EnumModuleSize.UNKNOWN){
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
					EnumModuleSize currentSize = null;
					for(IModuleState<IModuleDrive> drive : drives){
						currentSize = EnumModuleSize.getNewSize(currentSize, drive.getModule().getSize());
					}
					currentSize = EnumModuleSize.getNewSize(currentSize, ((IModuleDrive)container.getModule()).getSize());
					if(currentSize == EnumModuleSize.UNKNOWN){
						return false;
					}
				}
			}
		}else if(container.getModule() instanceof IModuleDrawer || container.getModule() instanceof IModuleCasing){
			if(slot.getModuleClass() == null){
				return false;
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
