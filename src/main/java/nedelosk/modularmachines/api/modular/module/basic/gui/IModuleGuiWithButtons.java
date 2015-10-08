package nedelosk.modularmachines.api.modular.module.basic.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.nedeloskcore.api.machines.IGuiBase;

public interface IModuleGuiWithButtons extends IModuleGui{

	@SideOnly(Side.CLIENT)
	void addButtons(IGuiBase gui, IModular modular);
	
}
