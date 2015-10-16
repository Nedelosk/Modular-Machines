package nedelosk.modularmachines.api.modular.module.tool.producer.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.nedeloskcore.api.machines.IGuiBase;

public interface IProducerGuiWithButtons extends IProducerGui {

	@SideOnly(Side.CLIENT)
	void addButtons(IGuiBase gui, IModular modular, ModuleStack stack);
	
}
