package nedelosk.modularmachines.api.modular.module.tool.producer.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;

public interface IProducerGuiWithWidgets extends IProducerGui {

	@SideOnly(Side.CLIENT)
	void addWidgets(IGuiBase gui, IModular modular, ModuleStack stack);
	
}
