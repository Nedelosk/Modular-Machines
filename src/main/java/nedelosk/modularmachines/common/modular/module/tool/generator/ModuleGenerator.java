package nedelosk.modularmachines.common.modular.module.tool.generator;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.module.IModuleGenerator;
import nedelosk.modularmachines.api.modular.module.Module;
import nedelosk.nedeloskcore.api.machines.IContainerBase;
import nedelosk.nedeloskcore.api.machines.IGuiBase;

public class ModuleGenerator extends Module implements IModuleGenerator {

	@Override
	public void addSlots(IContainerBase container, IModular modular) {
		
	}

	@Override
	public void addButtons(IGuiBase gui, IModular modular) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addWidgets(IGuiBase gui, IModular modular) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getModuleName() {
		return "Generator";
	}

}
