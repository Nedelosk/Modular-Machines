package nedelosk.modularmachines.common.modular.module.manager;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.module.Module;
import nedelosk.modularmachines.api.modular.module.manager.IModuleFanManager;
import nedelosk.nedeloskcore.api.machines.IContainerBase;
import nedelosk.nedeloskcore.api.machines.IGuiBase;

public class ModuleFanManager extends Module implements IModuleFanManager{

	public ModuleFanManager() {
	}
	
	@Override
	public void addSlots(IContainerBase container, IModular modular) {
		
	}

	@Override
	public void addButtons(IGuiBase gui, IModular modular) {
		
	}

	@Override
	public void addWidgets(IGuiBase gui, IModular modular) {
		
	}

	@Override
	public String getModuleName() {
		return "FanManager";
	}

}
