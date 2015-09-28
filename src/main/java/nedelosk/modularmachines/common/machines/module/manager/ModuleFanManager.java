package nedelosk.modularmachines.common.machines.module.manager;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.basic.machine.modular.IModular;
import nedelosk.modularmachines.api.basic.machine.module.ModuleGui;
import nedelosk.modularmachines.api.basic.machine.module.manager.IModuleFanManager;
import nedelosk.nedeloskcore.api.machines.IGuiBase;

public class ModuleFanManager extends ModuleGui implements IModuleFanManager{

	public ModuleFanManager() {
	}

	@Override
	public void addButtons(IGuiBase gui, IModular modular) {
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addWidgets(IGuiBase gui, IModular modular) {
		
	}

	@Override
	public String getModuleName() {
		return "FanManager";
	}

}
