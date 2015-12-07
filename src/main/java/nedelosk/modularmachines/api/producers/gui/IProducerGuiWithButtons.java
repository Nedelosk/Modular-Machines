package nedelosk.modularmachines.api.producers.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface IProducerGuiWithButtons extends IProducerGui {

	@SideOnly(Side.CLIENT)
	void addButtons(IGuiBase gui, IModular modular, ModuleStack stack);

}
